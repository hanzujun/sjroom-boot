package com.dazong.common.cache.redis.core;

import ai.grakn.redismock.RedisServer;
import com.dazong.common.autoconfig.RedisAutoConfigure;
import com.dazong.common.cache.constants.IExpire;
import com.dazong.common.cache.core.ICacheHandler;
import com.dazong.common.cache.redis.domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisAutoConfigure.class)
@EnableAutoConfiguration
public class RedisCacheHandlerTest {


    @Autowired
    ICacheHandler redisCacheHandler;

    private static RedisServer server = null;
    @Value("${spring.redis.port}")
    int port;

    @Before
    public void before() throws IOException {
        server = RedisServer.newRedisServer(port);
        server.start();
    }

    @Test
    public void test() {
    }

    @After
    public void after() {
        server.stop();
        server = null;
    }

    @Test
    public void saveString() {
        String keyTemp = "name";
        String valueOri = "abc";

        redisCacheHandler.saveString(keyTemp, valueOri, IExpire.FIVE_MIN);
        String valueResult = redisCacheHandler.getString(keyTemp);

        assert (valueOri.equals(valueResult));
    }
    @Test
    public void saveStringExpire() {
        final String keyTemp = "name";
        String valueOri = "daniel";

        redisCacheHandler.saveString(keyTemp, valueOri, IExpire.ONE_MILL_SECOND);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String valueResult = redisCacheHandler.getString(keyTemp);

        assert ("".equals(valueResult));
    }

    @Test
    public void saveObject() {
        String keyTemp = "name";

        Person personOri = new Person();
        personOri.setName("daniel");
        personOri.setAge(18);

        redisCacheHandler.saveObject(keyTemp, personOri, IExpire.FIVE_MIN);
        Person valueTemp = redisCacheHandler.getObject(keyTemp, Person.class);

        assert (personOri.equals(valueTemp));
    }
    @Test
    public void saveObjectExpire() throws InterruptedException {
        String keyTemp = "name";

        Person personOri = new Person();
        personOri.setName("daniel");
        personOri.setAge(18);

        redisCacheHandler.saveObject(keyTemp, personOri, IExpire.ONE_MILL_SECOND);
        for (int i = 0; i < 100000; ++i);
        Person valueTemp = redisCacheHandler.getObject(keyTemp, Person.class);

        assertEquals (valueTemp, null);
    }


    @Test
    public void saveList() {
        ICacheHandler cacheHandler = redisCacheHandler;
        List<String> strs2 = new ArrayList<String>();
        strs2.add("1");
        strs2.add("2");
        cacheHandler.delete("test:list:string");
        cacheHandler.saveList("test:list:string", strs2, IExpire.FIVE_SEC);
        List<String> strs = cacheHandler.getList("test:list:string",String.class);
        equals(strs,strs2);
    }
    private void equals(List list,List list2) {
        assertNotNull(list);
        assertEquals(list.size(),list2.size());
        for (int i =0,len = list.size();i < len;i++) {
            Object obj1 = list.get(i);
            Object obj2 = list.get(i);
            assertEquals(obj1,obj2);
        }
    }

    @Test
    public void delete() {
        String keyTemp = "name";

        Person personOri = new Person();
        personOri.setName("danielDelete");
        personOri.setAge(18);

        redisCacheHandler.saveObject(keyTemp, personOri, IExpire.FIVE_MIN);
        redisCacheHandler.delete(keyTemp);
        Person valueTemp = redisCacheHandler.getObject(keyTemp, Person.class);

        assertEquals (valueTemp, null);
    }

    /**
     * 需要补充更多测试用例
     */
    @Test
    public void incryBy(){
        {
            String key = "number1";
            Long delta = 100L;
            Long reult = redisCacheHandler.incrBy(key, delta,IExpire.EXPIRE_MAX);
            assertEquals(delta, reult);

        }
        {
            String key = "number1";
            Long delta = 1L;
            Long reult = redisCacheHandler.incrBy(key, delta,IExpire.EXPIRE_MAX);
            assertEquals(101L, reult.longValue());
        }

    }

    /**
     * 需要补充更多测试用例
     */
    @Test
    public void mapItemIncrBy(){
        {
            String key = "number";
            String itemKey = "mimi";
            redisCacheHandler.saveMapItem(key, itemKey, 0,IExpire.EXPIRE_MAX);

            Long delta = 100L;
            Long reult = redisCacheHandler.mapItemIncrBy(key, itemKey, delta,IExpire.EXPIRE_MAX);
            assertEquals(delta, reult);

        }
        {
            String key = "number";
            String itemKey = "mimi";
            Long delta = 1L;
            Long reult = redisCacheHandler.mapItemIncrBy(key, itemKey, delta,IExpire.EXPIRE_MAX);
            assertEquals(101L, reult.longValue());
        }


    }
}
