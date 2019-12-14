package com.sunvalley.framework.cache.cache.redis;

import com.sunvalley.framework.core.utils.NumberUtil;
import com.sunvalley.framework.core.utils.StringPool;
import com.sunvalley.framework.core.utils.StringUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.Map;

/**
 * redis cache 扩展cache name自动化配置
 *
 * @author L.cm
 */
public class RedisAutoCacheManager extends RedisCacheManager {

    public RedisAutoCacheManager(RedisCacheWriter cacheWriter,
                                 RedisCacheConfiguration defaultCacheConfiguration,
                                 Map<String, RedisCacheConfiguration> initialCacheConfigurations,
                                 boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        if (StringUtil.isBlank(name) || !name.contains(StringPool.HASH)) {
            return super.createRedisCache(name, cacheConfig);
        }
        String[] cacheArray = name.split(StringPool.HASH);
        if (cacheArray.length < 2) {
            return super.createRedisCache(name, cacheConfig);
        }
        String cacheName = cacheArray[0];
        if (cacheConfig != null) {
            long cacheAge = NumberUtil.toLong(cacheArray[1], -1);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(cacheAge));
        }
        return super.createRedisCache(cacheName, cacheConfig);
    }

}
