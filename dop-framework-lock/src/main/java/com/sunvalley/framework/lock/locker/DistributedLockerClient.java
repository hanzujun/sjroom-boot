package com.sunvalley.framework.lock.locker;

import com.sunvalley.framework.lock.UnableToAcquireLockException;

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 11:46
 */
public interface DistributedLockerClient {

    /**
     * 获取锁
     *
     * @param lockName 锁名
     * @return 锁名
     * @throws UnableToAcquireLockException
     */
    String lock(String lockName) throws UnableToAcquireLockException;

    /**
     * 获取锁
     *
     * @param lockName  锁名
     * @param leaseTime 过期时间
     * @param waitTime  等待锁时间
     * @return 锁名
     * @throws UnableToAcquireLockException
     */
    String lock(String lockName, int leaseTime, int waitTime) throws UnableToAcquireLockException;

    /**
     * 解锁（不忽略解锁异常）
     *
     * @param lockName 锁名
     * @throws UnableToAcquireLockException
     */
    void unlock(String lockName) throws UnableToAcquireLockException;

    /**
     * 解锁
     *
     * @param lockName 锁名
     * @param ignoreException 是否忽略异常（获取不到当前线程的锁时可忽略异常）
     * @throws UnableToAcquireLockException
     */
    void unlock(String lockName, boolean ignoreException) throws UnableToAcquireLockException;
}
