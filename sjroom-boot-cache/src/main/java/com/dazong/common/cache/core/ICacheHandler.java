package com.dazong.common.cache.core;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:缓存操作服务，抽象缓存操作的各种操作场景和提供（可以是redis,memcached,oscached等），此接口只处理存储，不处理并发和锁问题
 */
public interface ICacheHandler {
    /**
     * 保存字符串
     * @param key
     * @param value
     * @param expireMilliseconds
     */
    public void saveString(final String key, final String value, final int expireMilliseconds) ;

    /**
     * 保存对象
     * @param key
     * @param object
     * @param expireMilliseconds
     */
    public void saveObject(final String key, final Object object, final int expireMilliseconds) ;

    /**
     * 保存Map对象
     * @param key
     * @param data
     * @param expireMilliseconds
     */
    public void saveMap(final String key,final Map<String, ?> data, final int expireMilliseconds);

    /**
     * 保存Map对象的某个key
     * @param key
     * @param itemKey
     * @param value
     * @param expireMilliseconds
     */
    public void saveMapItem(final String key,final String itemKey,final Object value, final int expireMilliseconds);

    /**
     * 保存List
     * @param key
     * @param data
     * @param expireMilliseconds
     */
    public void saveList(final String key,final List data, final int expireMilliseconds);

    /**
     * 保存List
     * @param key
     * @param object
     * @param expireMilliseconds
     */
    public void saveListItem(final String key,final Object object, final int expireMilliseconds);

    /**
     * 删除key
     * @param key
     */
    public void delete(final String key) ;

    /**
     * 删除key
     * @param key
     * @param itemKey
     */
    public void deleteMapItem(final String key, final String itemKey) ;

    /**
     * 获取key
     * @param key
     * @return
     */
    public String getString(final String key) ;

    /**
     * 获取key
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(final String key, final Class<T> clazz);

    /**
     * 获取key
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T>List<T> getList(final String key,final Class<T> type);

    /**
     * Increment an integer value stored of {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return
     * @see <a href="http://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
     */
    public Long incrBy(final String key, final long value, final int expireMilliseconds);

    /**
     * map中item增减原子性操作
     * @param key
     * @param itemKey
     * @param value
     * @param expireMilliseconds
     * @return
     */
    public Long mapItemIncrBy(final String key,final String itemKey,final long value, final int expireMilliseconds);

    /**
     * 查询key
     * @param pattern   可带通配符
     * @return
     */
    Set<String> keys(String pattern);
}
