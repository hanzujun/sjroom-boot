//package com.sunvalley.framework.cache;
//
//import com.sunvalley.framework.cache.autoconfig.RedisAutoConfigure;
//import com.sunvalley.framework.cache.cache.constants.CacheExpire;
//import com.sunvalley.framework.cache.cache.core.impl.RedisClient;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RedisAutoConfigure.class)
//@EnableAutoConfiguration
//public class RedisConfigTest {
//    @Autowired
//    private RedisClient redisClient;
//
//    String key = "test:manson";
//
////    @Test
////    public void getKey() throws Exception {
////        UserVo vo = redisClient.getObject(key, UserVo.class);
////        System.out.println(vo);
////    }
////
////    @Test
////    public void testEntity() throws Exception {
////        UserVo userVo = new UserVo();
////        userVo.setAddress("上海");
////        userVo.setName("测试dfas");
////        userVo.setAge(123);
////        redisClient.saveObject(key, userVo, CacheExpire.TEN_MIN);
////        UserVo vo = redisClient.getObject(key, UserVo.class);
////        System.out.println(vo);
////    }
////
////    @Test
////    public void testString() throws Exception {
////        redisClient.saveObject("test:manson:test1", "test:manson", CacheExpire.FIVE_MIN);
////        System.out.println("testString:" + redisClient.getString("test:manson:test1"));
////    }
////
////    @Test
////    public void testList() throws Exception {
////        UserVo userVo = new UserVo();
////        userVo.setAddress("北京");
////        userVo.setName("jantent");
////        userVo.setAge(23);
////        UserVo auserVo = new UserVo();
////        auserVo.setAddress("n柜昂周");
////        auserVo.setName("antent");
////        auserVo.setAge(23);
////
////        List<UserVo> result = new ArrayList<>();
////
////        result.add(userVo);
////        result.add(auserVo);
////        redisClient.saveObject(key, result, CacheExpire.FIVE_SEC);
////        List<UserVo> result1 = (List<UserVo>) redisClient.getObject(key, UserVo.class);
////
////        System.out.println(result1);
////    }
////
////    @Test
////    public void testSubList() throws Exception {
////        UserVo userVo = new UserVo();
////        userVo.setAddress("北京");
////        userVo.setName("jantent");
////        userVo.setAge(23);
////
////        ProductDetailVo productDetailVo = new ProductDetailVo();
////        productDetailVo.setName("test1");
////
////
////        ProductDetailVo productDetailVo1 = new ProductDetailVo();
////        productDetailVo1.setName("test2");
////
////
////        List<ProductDetailVo> productDetailVoList = new ArrayList<>();
////        productDetailVoList.add(productDetailVo);
////        productDetailVoList.add(productDetailVo1);
////        userVo.setProductDetailVoList(productDetailVoList);
////
////        redisClient.saveObject(key, userVo, CacheExpire.FIVE_SEC);
////        UserVo result1 = (UserVo) redisClient.getObject(key, UserVo.class);
////
////        System.out.println(result1);
////    }
//
//}
