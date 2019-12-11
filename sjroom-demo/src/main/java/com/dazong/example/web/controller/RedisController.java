package com.dazong.example.web.controller;

import com.github.sjroom.cache.constants.CacheType;
import com.github.sjroom.cache.constants.IExpire;
import com.github.sjroom.cache.core.ICacheHandler;
import com.github.sjroom.cache.manager.CacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DanielLi
 * @date: 2018/1/12
 * @description:缓存框架使用说明
 */
@RestController
public class RedisController {

    @Autowired
    ICacheHandler redisCacheHandler;

    @Autowired
    CacheFactory cacheFactory;

    /**
     * 使用自动注入处理器对象
     */

    /**
     * 设置key值
     */
    @RequestMapping("/set1")
    @ResponseBody
    public void testSetList1() {
        List<String> stringList = new ArrayList<>();
        String key = "demo";
        stringList.add("hello");
        stringList.add("world");
        redisCacheHandler.saveList(key, stringList, IExpire.FIVE_MIN);
    }

    /**
     * 获取key值
     */
    @RequestMapping("/get1")
    @ResponseBody
    public List<String> testGetList() {
        String key = "demo";
        return redisCacheHandler.getList(key, String.class);
    }

    /**
     * 使用工厂获取实例
     */
    /**
     * 设置key值
     */
    @RequestMapping("/set2")
    @ResponseBody
    public void testSetList2() {
        List<String> stringList = new ArrayList<>();
        String key = "demo";
        stringList.add("hello");
        stringList.add("world");
        cacheFactory.getCacheHandler(CacheType.CACHE_REDIS).saveList(key, stringList, IExpire.FIVE_MIN);
    }

    /**
     * 获取key值
     */
    @RequestMapping("/get2")
    @ResponseBody
    public List<String> testGetList2() {
        String key = "demo";
        return cacheFactory.getCacheHandler(CacheType.CACHE_REDIS).getList(key, String.class);
    }

}
