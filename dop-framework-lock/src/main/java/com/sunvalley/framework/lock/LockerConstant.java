package com.sunvalley.framework.lock;

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 11:54
 */
public interface LockerConstant {
    String REDISSON_SINGLE_PREFIX = "redis://";
    String LOCKER_PREFIX = "LOCK:";
    int DEFAULT_DATABASE = 0;
    int DEFAULT_POOL_SIZE = 20;
    int DEFAULT_IDLE_SIZE = 5;
    int DEFAULT_IDLE_TIMEOUT = 60000;
    int DEFAULT_CONNECTION_TIMEOUT = 3000;
    int DEFAULT_TIMEOUT = 10000;
    int DEFAULT_LEASE_TIME = 100;
    int DEFAULT_WAIT_TIME = 30;

}
