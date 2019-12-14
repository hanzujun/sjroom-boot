package com.sunvalley.framework.cache.cache.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

/**
 * cache key 封装
 *
 * @author L.cm
 */
@Getter
@ToString
@AllArgsConstructor
public class CacheKey {
    /**
     * cache key
     */
    private final String key;
    /**
     * 超时时间 秒
     */
    private final Duration expire;
}
