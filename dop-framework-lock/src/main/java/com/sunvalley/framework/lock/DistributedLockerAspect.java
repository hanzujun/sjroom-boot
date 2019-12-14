package com.sunvalley.framework.lock;

import com.sunvalley.framework.core.exception.BusinessException;
import com.sunvalley.framework.core.exception.FrameworkException;
import com.sunvalley.framework.lock.UnableToAcquireLockException;
import com.sunvalley.framework.lock.annotation.AnnotationDistributedLocker;
import com.sunvalley.framework.lock.annotation.DistributedLocked;
import com.sunvalley.framework.lock.annotation.spel.DopExpressionEvaluator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.Ordered;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
@Slf4j
@Aspect
@Configuration
public class DistributedLockerAspect implements Ordered {

    @Autowired
    private AnnotationDistributedLocker annotationDistributedLocker;
    @Autowired
    private DopExpressionEvaluator expressionEvaluator;


    @Around("@annotation(distributedLocked)")
    public Object distributedLock(ProceedingJoinPoint pjp, DistributedLocked distributedLocked) {
        return doLock(readMetadata(distributedLocked), pjp);
    }

    private Object doLock(LockMetadata lockMetadata, ProceedingJoinPoint pjp) {
        String lockName = resolverKey(pjp, lockMetadata.getLockName()) ;
        try {
            return annotationDistributedLocker.lock(lockName, lockMetadata.getLeaseTime(), lockMetadata.getWaitTime(),
                () -> proceed(pjp));
        } catch (UnableToAcquireLockException e) {
            if (!(lockMetadata.isIgnoreUnableToAcquiredLockException() || lockMetadata.isIgnoreException())) {
                throw e;
            }
            if (log.isWarnEnabled()) {
                log.warn("Failed to acquire distributed lock with name {}, lease time {}, wait time {} at method {}",
                    lockName, lockMetadata.getLeaseTime(), lockMetadata.getWaitTime(), pjp.getSignature().getName());
            }
        } catch (BusinessException e) {
            if (!lockMetadata.isIgnoreException()) {
                throw e;
            }
            if (log.isWarnEnabled()) {
                log.warn("Failed to executing method \"" + pjp.getSignature().getName() + "\".", e);
            }
        } catch (FrameworkException e) {
            if (!lockMetadata.isIgnoreException()) {
                throw e;
            }
            if (log.isWarnEnabled()) {
                log.warn("Failed to executing method \"" + pjp.getSignature().getName() + "\".", e);
            }
        } catch (Throwable e) {
            if (!lockMetadata.isIgnoreException()) {
                throw new FrameworkException(e);
            }
            if (log.isWarnEnabled()) {
                log.warn("Unexpected throwable occurred at executing method \"" + pjp.getSignature().getName() + "\".", e);
            }
        }
        return null;
    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable e) {
            throw new FrameworkException(e);
        }
    }

    private String resolverKey(JoinPoint joinPoint, String keyExpression) {
        Assert.hasText(keyExpression, "Lock name cloud not be blank on @DistributedLocked.");

        Object target = joinPoint.getTarget();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, target.getClass());
        EvaluationContext context = expressionEvaluator
            .createEvaluationContext(target, target.getClass(), method, joinPoint.getArgs());
        return String.valueOf(expressionEvaluator.key(keyExpression, elementKey, context));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 10;
    }

    private LockMetadata readMetadata(DistributedLocked distributedLocked) {
        return new LockMetadata(distributedLocked.lockName(), distributedLocked.waitTime(), distributedLocked.leaseTime(), distributedLocked.ignoreException(), distributedLocked.ignoreUnableToAcquiredLockException());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LockMetadata {
        /**
         * 锁名
         */
        private String lockName;
        /**
         * 等待锁超时时间，单位：秒
         * 默认30s
         */
        private int waitTime;

        /**
         * 自动解锁时间，单位秒
         * 自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放
         * 默认100s
         */
        private int leaseTime;

        /**
         * 忽略所有异常，否则会往外抛
         */
        private boolean ignoreException;

        /**
         * 忽略没有获取到锁的异常，默认为true
         */
        private boolean ignoreUnableToAcquiredLockException;
    }
}
