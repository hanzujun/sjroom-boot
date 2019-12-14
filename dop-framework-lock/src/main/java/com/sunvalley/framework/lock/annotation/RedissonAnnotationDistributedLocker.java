package com.sunvalley.framework.lock.annotation;

import com.sunvalley.framework.core.utils.StringUtil;
import com.sunvalley.framework.lock.LockerConstant;
import com.sunvalley.framework.lock.UnableToAcquireLockException;
import com.sunvalley.framework.lock.annotation.AnnotationDistributedLocker;
import com.sunvalley.framework.lock.utils.LockerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 基于Redisson的redis分布式锁
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
@Slf4j
@AllArgsConstructor
public class RedissonAnnotationDistributedLocker implements AnnotationDistributedLocker, InitializingBean {

    private final RedissonClient redissonClient;

    private final Integer defaultLeaseTime;

    private final Integer defaultWaitTime;

    @Override
    public <T> T lock(String lockName, Supplier<T> supplier)
        throws UnableToAcquireLockException {
        return lock(lockName, this.defaultLeaseTime, defaultWaitTime, supplier);
    }

    @Override
    public <T> T lock(String lockName, int leaseTime, int waitTime, Supplier<T> supplier)
        throws UnableToAcquireLockException {
        RLock lock = redissonClient.getLock(LockerUtil.getLockName(lockName));
        Instant startInstant = Instant.now();
        try {
            boolean success = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (success) {
                if (log.isDebugEnabled()) {
                    log.debug("Successfully acquired distributed lock {}, wait time {}/ms. ", lockName, Duration.between(startInstant, Instant.now()).toMinutes());
                }
                return supplier.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UnableToAcquireLockException(e);
        } finally {
            lock.unlock();
            if (log.isDebugEnabled()) {
                log.debug("Successfully release distributed lock {}. used lock time {}/ms", lockName, Duration.between(startInstant, Instant.now()).toMinutes());
            }
        }
        throw new UnableToAcquireLockException(StringUtil.format(
            "Failed to acquire distributed lock with name {}, lease time {}, wait time {} .", lockName, leaseTime, waitTime));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.defaultLeaseTime, "Default expire time must not be null, please configure with key \"dop.lock.defaultLeaseTime\"");
        Assert.notNull(this.defaultWaitTime, "Default expire time must not be null, please configure with key \"dop.lock.defaultWaitTime\"");
        Assert.notNull(this.redissonClient, "Redisson client can not be null.");
    }

}
