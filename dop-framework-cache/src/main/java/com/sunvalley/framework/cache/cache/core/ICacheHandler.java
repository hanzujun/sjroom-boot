package com.sunvalley.framework.cache.cache.core;


import java.util.function.Supplier;

/**
 * 缓存操作服务，抽象缓存操作的各种操作场景和提供（可以是redis,memcached,oscached等），此接口只处理存储，不处理并发和锁问题
 *
 * @author dream.lu
 */
public interface ICacheHandler {

    /**
     * 保存对象
     *
     * @param cacheKey 缓存key
     * @param object   对象
     */
    default void put(final CacheKey cacheKey, final Object object) {

    }

    /**
     * 获取对象,需要反序列化
     *
     * @param cacheKey 缓存key
     * @param <T>      泛型
     * @return 对象
     */
    default <T> T get(final CacheKey cacheKey) {
        return null;
    }

    /**
     * 获取对象,需要反序列化
     *
     * @param cacheKey 缓存key
     * @param loader   加载器
     * @param <T>      泛型
     * @return 对象
     */
    default <T> T get(final CacheKey cacheKey, Supplier<T> loader) {
        return null;
    }

    /**
     * 获取String,需要反序列化
     */
    default String getString(final CacheKey cacheKey) {
        return null;
    }

    /**
     * 删除key
     *
     * @param cacheKey 缓存key
     */
    default void evict(final CacheKey cacheKey) {

    }

}
