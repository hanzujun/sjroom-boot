package com.sunvalley.framework.lock.locker;

import com.sunvalley.framework.core.utils.StringUtil;
import com.sunvalley.framework.lock.UnableToAcquireLockException;
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

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 11:53
 */
@Slf4j
@AllArgsConstructor
public class RedissonDistributedLockerClient implements DistributedLockerClient, InitializingBean {

    private final RedissonClient redissonClient;

    private final Integer defaultLeaseTime;

    private final Integer defaultWaitTime;

    @Override
    public String lock(String lockName) throws UnableToAcquireLockException {
        return lock(lockName, defaultLeaseTime, defaultWaitTime);
    }

    @Override
    public String lock(String lockName, int leaseTime, int waitTime) throws UnableToAcquireLockException {
        lockName = LockerUtil.getLockName(lockName);
        RLock lock = redissonClient.getLock(lockName);
        Instant startInstant = Instant.now();
        try {
            boolean locked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (locked) {
                log.debug("Successfully acquired distributed lock {}, wait time {}/ms. ", lockName, Duration.between(startInstant, Instant.now()).toMinutes());
                return lockName;
            }
            throw new UnableToAcquireLockException(
                StringUtil.format("Failed to acquire distributed lock with name {}, lease time {}, wait time {} .", lockName, leaseTime, waitTime));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UnableToAcquireLockException(e);
        }
    }

    @Override
    public void unlock(String lockName) throws UnableToAcquireLockException {
        unlock(lockName, false);
    }

    @Override
    public void unlock(String lockName, boolean ignoreException) throws UnableToAcquireLockException {
        lockName = LockerUtil.getLockName(lockName);
        RLock lock = redissonClient.getLock(lockName);
        try {
            lock.unlock();
            log.debug("Successfully to release distributed lock {}.", lockName);
        } catch (IllegalMonitorStateException e) {
            if (!ignoreException) {
                throw new UnableToAcquireLockException(e.getMessage());
            }
            log.warn("Failure to release distributed lock {}.", lockName);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.defaultLeaseTime, "Default expire time must not be null, please configure with key \"dop.lock.defaultLeaseTime\"");
        Assert.notNull(this.defaultWaitTime, "Default expire time must not be null, please configure with key \"dop.lock.defaultWaitTime\"");
        Assert.notNull(this.redissonClient, "Redisson client can not be null.");
    }
}
