package com.sunvalley.framework.lock.annotation.spel;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Katrel.Zhou
 * @date 2019/8/2 16:08
 */
public class DistributedLockerExpressionEvaluator extends CachedExpressionEvaluator
    implements DopExpressionEvaluator {

    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> methodCache = new ConcurrentHashMap<>(64);


    @Override
    public EvaluationContext createEvaluationContext(Object target, Class<?> targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        ExpressionRootObject root = new ExpressionRootObject(target, args);
        return new MethodBasedEvaluationContext(root, targetMethod, args, paramNameDiscoverer);
    }

    @Override
    public Object key(String keyExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext) {
        return getExpression(keyCache, elementKey, keyExpression).getValue(evalContext);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = methodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            if (targetMethod == null) {
                targetMethod = method;
            }
            methodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}
