## 大宗缓存框架

---

### 简介
缓存框架，目前支持Redis缓存和本地缓存


### 使用说明
1、在pom.xml文件引入dz-common-cache
```xml
<!--配置dz-common-cache-->
<dependency>
    <groupId>com.dazong.common</groupId>
    <artifactId>dz-common-cache</artifactId>
    <version>4.0.0-SNAPSHOT</version>
</dependency>
```

2、在spring配置文件加入：
```xml
<!--如果项目为非springboot，则需要再加载这个bean，是springboot项目，不需要配置-->
  <bean id="redisAutoConfigure" class="RedisAutoConfigure"></bean>
  ```
  
  
2、在properties中加入redis配置属性
  ```Java
  #Redis 配置
  ############################################################################################
  # 单点配置
  spring.redis.host=172.16.21.20
  # 集群配置
  #spring.redis.cluster.nodes=172.16.21.20:7000,172.16.21.20:7001,172.16.21.20:7002,172.16.21.20:7003,172.16.21.20:7004,172.16.21.20:7005
  #通用配置
  spring.redis.port=6379
  spring.redis.password=
  #Redis数据库索引(默认为0)
  spring.redis.database=0
  ############################################################################################
  ```

4、使用方式
```java
  
    @Autowired
    ICacheHandler redisCacheHandler;

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
```


### Release Note

#### 1.0
- 支持Redis缓存框架
- 支持spring boot自动注入和非spring boot项目