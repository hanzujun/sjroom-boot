package com.sunvalley.framework.cache.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.sunvalley.framework.core.utils.NumberUtil;
import com.sunvalley.framework.core.utils.StringPool;
import com.sunvalley.framework.core.utils.StringUtil;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine cache 扩展
 *
 * @author dream.lu
 */
public class CaffeineAutoCacheManager extends org.springframework.cache.caffeine.CaffeineCacheManager {

    public CaffeineAutoCacheManager() {
        super();
    }

    @Override
    protected Cache createCaffeineCache(String name) {
        if (StringUtil.isBlank(name) || !name.contains(StringPool.HASH)) {
            return super.createCaffeineCache(name);
        }
        String[] cacheArray = name.split(StringPool.HASH);
        if (cacheArray.length < 2) {
            return super.createCaffeineCache(name);
        }
        String cacheName = cacheArray[0];
        long cacheAge = NumberUtil.toLong(cacheArray[1], -1);
        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(cacheAge, TimeUnit.SECONDS)
            .build();
        return new CaffeineCache(cacheName, cache, isAllowNullValues());
    }

}
