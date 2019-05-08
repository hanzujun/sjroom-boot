package com.dazong.common.cache.core.impl;

import com.github.sjroom.common.response.R;
import com.github.sjroom.common.util.JdkSerializerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:Redis文档说明:http://blog.csdn.net/zhu_xun/article/details/16806285
 */

@Service
@Slf4j
public class RedisCacheHandler extends AbstractCacheHandler {

    public static final String IS_NULL_VALUE_WARN = "获取到的value值为null";

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 获取字符串
     */

    @Override
    public void saveString(final String key,final String str, final int expireMilliseconds) {
        R.required(key);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                redisConnection.pSetEx(key.getBytes(), expireMilliseconds, str.getBytes());
                return true;
            }
        });
    }

    /**
     * 获取对象,需要序列化
     * @param key
     * @param object
     * @param expireMilliseconds
     */
    @Override
    public void saveObject(final String key, final Object object, final int expireMilliseconds) {
        R.required(key);
        R.required(object);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                redisConnection.pSetEx(key.getBytes(), expireMilliseconds, JdkSerializerUtil.serialize(object));
                return true;
            }
        });
    }


    /**
     * 获取Map,需要序列化每一个元素
     * @param key
     * @param data
     * @param expireMilliseconds
     */
    @Override
    public void saveMap(final String key,final  Map<String, ?> data, final int expireMilliseconds) {
        R.required(key);
        R.required(data);

        final Map<byte[],byte[]> map = new HashMap<>(10);
        for (Map.Entry<String,?> entry : data.entrySet()) {
            map.put(entry.getKey().getBytes(),JdkSerializerUtil.serialize(entry.getValue()));
        }

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                redisConnection.hMSet(key.getBytes(), map);
                redisConnection.pExpire(key.getBytes(), expireMilliseconds);
                return true;
            }
        });
    }

    /**
     * 向名称为key的hash中添加元素field<—>value
     * @param key
     * @param itemKey
     * @param value
     * @param expireMilliseconds
     */
    @Override
    public void saveMapItem(final String key,final String itemKey,final Object value, final int expireMilliseconds) {
        R.required(key);
        R.required(itemKey);
        R.required(value);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                redisConnection.hSet(key.getBytes(), itemKey.getBytes(), JdkSerializerUtil.serialize(value));
                redisConnection.pExpire(key.getBytes(), expireMilliseconds);
                return true;
            }
        });
    }

    /***
     * 保存List,需要序列化
     * @param key
     * @param data
     * @param expireMilliseconds
     */
    @Override
    public void saveList(final String key,final List data, final int expireMilliseconds) {
        R.required(key);
        R.required(data);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                for (Iterator iter = data.iterator(); iter.hasNext();) {
                    redisConnection.lPush(key.getBytes(), JdkSerializerUtil.serialize(iter.next()));
                    redisConnection.pExpire(key.getBytes(), expireMilliseconds);
                }
                return true;
            }
        });
    }

    /**
     * 在名称为key的list头添加一个值为value的 元素
     * @param key
     * @param object
     * @param expireMilliseconds
     */
    @Override
    public void saveListItem(final String key,final Object object, final int expireMilliseconds) {
        R.required(key);
        if (object == null) {
            return;
        }
            redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection){
                    redisConnection.lPush(key.getBytes(), JdkSerializerUtil.serialize(object));
                    redisConnection.pExpire(key.getBytes(), expireMilliseconds);
                    return true;
                }
            });
    }

    /**
     * 删除元素
     * @param key
     */
    @Override
    public void delete(final String key) {
        R.required(key);
//        redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection redisConnection){
//                redisConnection.del(key.getBytes());
//                return true;
//            }
//        });
        redisTemplate.delete(key);
    }

    @Override
    public void deleteMapItem(final String key, final String itemKey) {
        R.required(key);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                redisConnection.hDel(key.getBytes(),itemKey.getBytes());
                return true;
            }
        });
    }

    /**
     * 获取字符串
     * @param key
     * @return
     */
    @Override
    public String getString(final String key) {
        R.required(key);

        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection){
                byte[] bytes = connection.get(key.getBytes());
                if(null == bytes){
                    log.info(IS_NULL_VALUE_WARN);
                    return "";
                }
                return new String(bytes);
            }
        });
    }

    /**
     * 获取对象,需要反序列化
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getObject(final String key, final Class<T> clazz) {
        R.required(key);
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取列表,需要反序列化
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T>List<T> getList(final String key,final Class<T> type) {
        R.required(key);
        R.required(type);
        return redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection connection){
                Long len = connection.lLen(key.getBytes());
                if(len <= 0){
                    log.info(IS_NULL_VALUE_WARN);
                    return new ArrayList<>();
                }
                List<byte[]> list  = connection.lRange(key.getBytes(), 0L, len);
                List<T> targetlist = new ArrayList<>(len.intValue());
                for (byte[] l : list) {
                    targetlist.add(JdkSerializerUtil.deserialize(l,type));
                }
                return targetlist;
            }
        });
    }
    /**
     * Increment an integer value stored of {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return
     * @see <a href="http://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
     */
    @Override
    public Long incrBy(final String key, final long value, final int expireMilliseconds){
        R.required(key);

        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection){
                Long result = redisConnection.incrBy(key.getBytes(), value);
                redisConnection.pExpire(key.getBytes(), expireMilliseconds);
                return result;
            }
        });
    }

    /**
     * map中item增减原子性操作
     *
     * @param key
     * @param itemKey
     * @param value
     * @param expireMilliseconds
     * @return
     */
    @Override
    public Long mapItemIncrBy(final String key, final String itemKey, final long value, final int expireMilliseconds) {
        R.required(key);
        R.required(itemKey);

        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection){
                Long result = redisConnection.hIncrBy(key.getBytes(),itemKey.getBytes(),value);
                redisConnection.pExpire(key.getBytes(), expireMilliseconds);
                return result;
            }
        });
    }

    /**
     * 查询key
     *
     * @param pattern 可带通配符
     * @return
     */
    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
