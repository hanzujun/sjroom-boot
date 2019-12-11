package com.github.sjroom.cache.redis.core;

import com.github.sjroom.autoconfig.RedisAutoConfigure;
import com.github.sjroom.cache.constants.CacheType;
import com.github.sjroom.cache.core.ICacheHandler;
import com.github.sjroom.cache.core.impl.LocalCacheHandler;
import com.github.sjroom.cache.core.impl.MemcacheHandler;
import com.github.sjroom.cache.core.impl.RedisCacheHandler;
import com.github.sjroom.cache.manager.CacheFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: DanielLi
 * @date: 2018/1/12
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisAutoConfigure.class)
@EnableAutoConfiguration
public class CatchFactoryTest {

    @Autowired
    ICacheHandler redisCacheHandler;

    @Autowired
    CacheFactory cacheFactory;

    @Test
    public void testGetDefaultHandler(){
        ICacheHandler cacheHandler = cacheFactory.getCacheHandler(CacheType.CACHE_REDIS);
        assert(cacheHandler instanceof RedisCacheHandler);
    }

    @Test
    public void testGetHandler1(){
        ICacheHandler cacheHandler = cacheFactory.getCacheHandler(CacheType.CACHE_REDIS);
        assert(cacheHandler instanceof RedisCacheHandler);
    }
    @Test
    public void testGetHandler2(){
        ICacheHandler cacheHandler = cacheFactory.getCacheHandler(CacheType.CACHE_MEMCACHE);
        assert(cacheHandler instanceof MemcacheHandler);
    }
    @Test
    public void testGetHandler3(){
        ICacheHandler cacheHandler = cacheFactory.getCacheHandler(CacheType.CACHE_LOCALCACHE);
        assert(cacheHandler instanceof LocalCacheHandler);
    }

    @Test
    public void testGetHandler4(){
        boolean retFlag = false;
        try{
            ICacheHandler cacheHandler = cacheFactory.getCacheHandler(null);
        }
        catch (NullPointerException e){
            retFlag = true;
        }
        catch (Exception e){
            retFlag = false;
        }
        assert (retFlag);

    }
    @Test
    public void testStringArray(){
        {
            String[] arrayBean = {};
            String msg = arrayBean.toString();
            assert(!msg.equals(""));
        }
    }

}