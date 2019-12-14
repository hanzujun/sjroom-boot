package com.sunvalley.framework.cache.cache.core;

import com.sunvalley.framework.core.utils.CharPool;
import com.sunvalley.framework.core.utils.ObjectUtil;
import com.sunvalley.framework.core.utils.StringPool;
import com.sunvalley.framework.core.utils.StringUtil;

import java.time.Duration;

/**
 * 缓存 key
 *
 * @author dream.lu
 */
public interface ICacheKey {
    /**
     * 获取前缀
     *
     * @return key 前缀
     */
    String getPrefix();

    /**
     * 超时时间
     *
     * @return 超时时间
     */
    default Duration getExpire() {
        return Duration.ZERO;
    }


    /**
     * 组装 key
     *
     * @param suffix 参数
     * @return cache key
     */
    default String getKey(Object... suffix) {
        String prefix = this.getPrefix();
        // 拼接参数
        if (ObjectUtil.isEmpty(suffix)) {
            return prefix;
        }
        return prefix + CharPool.COLON + StringUtil.join(suffix, StringPool.COLON);
    }

    /**
     * 组装 cache key
     *
     * @param suffix 参数
     * @return cache key
     */
    default CacheKey getFullKey(Object... suffix) {
        String prefix = this.getPrefix();
        // 拼接参数
        String key;
        if (ObjectUtil.isNotEmpty(suffix)) {
            key = prefix + CharPool.COLON + StringUtil.join(suffix, StringPool.COLON);
        } else {
            key = prefix;
        }
        Duration expire = this.getExpire();
        return new CacheKey(key, expire);
    }
}
