package com.sunvalley.framework.lock.annotation;

import java.lang.annotation.*;
/**
 * 分布式锁
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DistributedLocked {
    /**
     * 锁名，支持spel
     * @return
     */
    String lockName() default "";
    /**
     * 等待锁超时时间，单位：秒
     * 默认30s
     * @return
     */
    int waitTime() default 30;

    /**
     * 自动解锁时间，单位秒
     * 自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放
     * 默认100s
     * @return
     */
    int leaseTime() default 100;

    /**
     * 忽略所有异常，否则会往外抛
     * @return
     */
    boolean ignoreException() default false;

    /**
     * 忽略没有获取到锁的异常，默认为true
     *
     * @return
     */
    boolean ignoreUnableToAcquiredLockException() default true;
}
