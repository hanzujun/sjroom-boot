package com.sunvalley.framework.core.utils;


public class RedisKeyUtil {

    /**
     * redis的key
     * 形式为：
     * 表名:主键名:主键值:列名
     *
     * @param projectName 表名
     * @param key         主键名
     * @return
     */
    public static String getKey(String projectName, String key) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(projectName).append(":");
        buffer.append(key);
        return buffer.toString();
    }
}
