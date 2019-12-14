package com.sunvalley.framework.lock.utils;

import com.sunvalley.framework.lock.LockerConstant;

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 12:07
 */
public class LockerUtil {

    public static String getLockName(String lockName) {
        if (lockName.startsWith(LockerConstant.LOCKER_PREFIX)) {
            return lockName;
        }
        return LockerConstant.LOCKER_PREFIX + lockName;
    }
}
