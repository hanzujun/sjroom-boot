//package com.dazong.common.cache.redis.core;
//
//import ai.grakn.redismock.RedisServer;
//import RedisAutoConfigure;
//import IExpire;
//import ICacheHandler;
//import Person;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static junit.framework.TestCase.assertEquals;
//import static junit.framework.TestCase.assertNotNull;
//
///**
// * @author: DanielLi
// * @date: 2018/1/25
// * @description:目前AbstractCacheHandler没有代码实现,所以暂时UT不做断言
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RedisAutoConfigure.class)
//@EnableAutoConfiguration
//public class AbstractCacheHandlerTest {
//
//    @Autowired
//    ICacheHandler abstractCacheHandler;
//
//    private static RedisServer server = null;
//    @Value("${spring.redis.port}")
//    int port;
//
//    @Before
//    public void before() throws IOException {
//        server = RedisServer.newRedisServer(port);
//        server.start();
//    }
//
//    @Test
//    public void test() {
//    }
//
//    @After
//    public void after() {
//        server.stop();
//        server = null;
//    }
//
//    @Test
//    public void saveString() {
//        String keyTemp = "name";
//        String valueOri = "abc";
//
//        abstractCacheHandler.saveString(keyTemp, valueOri, IExpire.FIVE_MIN);
//        String valueResult = abstractCacheHandler.getString(keyTemp);
//
////        assert (valueOri.equals(valueResult));
//    }
//    @Test
//    public void saveStringExpire() {
//        final String keyTemp = "name";
//        String valueOri = "daniel";
//
//        abstractCacheHandler.saveString(keyTemp, valueOri, IExpire.ONE_MILL_SECOND);
//        String valueResult = abstractCacheHandler.getString(keyTemp);
//
////        assert ("".equals(valueResult));
//    }
//
//    @Test
//    public void saveObject() {
//        String keyTemp = "name";
//
//        Person personOri = new Person();
//        personOri.setName("daniel");
//        personOri.setAge(18);
//
//        abstractCacheHandler.saveObject(keyTemp, personOri, IExpire.FIVE_MIN);
//        Person valueTemp = abstractCacheHandler.getObject(keyTemp, Person.class);
//
////        assert (personOri.equals(valueTemp));
//    }
//    @Test
//    public void saveObjectExpire() throws InterruptedException {
//        String keyTemp = "name";
//
//        Person personOri = new Person();
//        personOri.setName("daniel");
//        personOri.setAge(18);
//
//        abstractCacheHandler.saveObject(keyTemp, personOri, IExpire.ONE_MILL_SECOND);
//        for (int i = 0; i < 100000; ++i);
//        Person valueTemp = abstractCacheHandler.getObject(keyTemp, Person.class);
//
////        assertEquals (valueTemp, null);
//    }
//
//    @Test
//    public void saveMap() {
//        ICacheHandler cacheHandler = abstractCacheHandler;
//        String mapkey = "test:map";
//        String itemkey = "str";
//        String itemValue = "test001";
//        cacheHandler.saveMapItem(mapkey,itemkey,itemValue, IExpire.FIVE_MIN);
//        String value = cacheHandler.getMapItem(mapkey,itemkey,String.class);
////        assertNotNull(value);
////        assertEquals(value,itemValue);
//
//        cacheHandler.saveMapItem(mapkey,"int",new Integer(100), IExpire.FIVE_MIN);
//        Integer intValue = cacheHandler.getMapItem(mapkey,"int",Integer.class);
////        assertNotNull(intValue);
////        assertEquals(intValue,new Integer(100));
//
//        cacheHandler.saveMapItem(mapkey,"long",new Long(100), IExpire.FIVE_MIN);
//        Long longValue = cacheHandler.getMapItem(mapkey,"long",Long.class);
////        assertNotNull(longValue);
////        assertEquals(longValue,new Long(100));
//
//        cacheHandler.saveMapItem(mapkey,"double",new Double(1000.222), IExpire.FIVE_MIN);
//        Double doubleValue = cacheHandler.getMapItem(mapkey,"double",Double.class);
////        assertNotNull(doubleValue);
////        assertEquals(doubleValue,new Double(1000.222));
//
//        cacheHandler.saveMapItem(mapkey,"bigdecimal",new BigDecimal(1000.222), IExpire.FIVE_MIN);
//        BigDecimal decimal = cacheHandler.getMapItem(mapkey,"bigdecimal",BigDecimal.class);
////        assertNotNull(decimal);
////        assertEquals(decimal,new BigDecimal(1000.222));
//
//        cacheHandler.saveMapItem("amap","one","1", IExpire.FIVE_MIN);
//        cacheHandler.saveMapItem("amap","two","2", IExpire.FIVE_MIN);
//        cacheHandler.saveMapItem("amap","three","3", IExpire.FIVE_MIN);
//        cacheHandler.saveMapItem("amap","four","4", IExpire.FIVE_MIN);
//        Map<String,String> allvalue = cacheHandler.getMap("amap",String.class);
////        assertNotNull(allvalue);
////        assertTrue(allvalue.size() == 4);
////        assertEquals("1",allvalue.get("one"));
////        assertEquals("2",allvalue.get("two"));
////        assertEquals("3",allvalue.get("three"));
////        assertEquals("4",allvalue.get("four"));
//    }
//
//
//    @Test
//    public void saveList() {
//        ICacheHandler cacheHandler = abstractCacheHandler;
//        List<String> strs2 = new ArrayList<String>();
//        strs2.add("1");
//        strs2.add("2");
//        cacheHandler.delete("test:list:string");
//        cacheHandler.saveList("test:list:string", strs2, IExpire.FIVE_SEC);
//        List<String> strs = cacheHandler.getList("test:list:string",String.class);
////        equals(strs,strs2);
//    }
//    private void equals(List list,List list2) {
//        assertNotNull(list);
//        assertEquals(list.size(),list2.size());
//        for (int i =0,len = list.size();i < len;i++) {
//            Object obj1 = list.get(i);
//            Object obj2 = list.get(i);
//            assertEquals(obj1,obj2);
//        }
//    }
//
//    @Test
//    public void delete() {
//        String keyTemp = "name";
//
//        Person personOri = new Person();
//        personOri.setName("danielDelete");
//        personOri.setAge(18);
//
//        abstractCacheHandler.saveObject(keyTemp, personOri, IExpire.FIVE_MIN);
//        abstractCacheHandler.delete(keyTemp);
//        Person valueTemp = abstractCacheHandler.getObject(keyTemp, Person.class);
//
////        assertEquals (valueTemp, null);
//    }
//}
