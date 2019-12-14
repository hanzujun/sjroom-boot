## dop-framework-cache
使用说明文档

### pom文件配置.

```xml
<dependency>
    <artifactId>dop-framework-cache</artifactId>
    <groupId>com.sunvalley</groupId>
</dependency>
```

## 注解缓存

### 注解列表

| 注解             | 说明                  |
| ---------------- | --------------------- |
| @RedisCacheAble  | 使用 redis 缓存       |
| @RedisCachePut   | 使用 redis 缓存       |
| @RedisCacheEvict | 使用 redis 缓存       |
| @CacheAble       | 使用内存缓存 Caffeine |
| @CachePut        | 使用内存缓存 Caffeine |
| @CacheEvict      | 使用内存缓存 Caffeine |

注意：cache 中扩展了基于 cacheName # 号分割超时时间，单位为 seconds（秒）。

### 示例

```java
@RedisCacheAble(value = "DOP_ORDER#300", key = "'ID:' + #id")
public String getById(Long id) {
	return "test1";
}

@Cacheable(value = "DOP_ORDER#300", key = "'ID:' + #id")
public String getById(Long id) {
	return "test1";
}
```

## 方法缓存

目前只提供了 redis 的。

```java

  @Autowired
  private RedisClient redisClient;

  @Test
  public void getKey() throws Exception {
      UserVo vo = redisClient.get(key);
      System.out.println(vo);
  }
  
  @Test
  public void testEntity() throws Exception {
      UserVo userVo = new UserVo();
      userVo.setAddress("上海");
      userVo.setName("测试dfas");
      userVo.setAge(123);
      redisClient.put(key, userVo);
      UserVo vo = redisClient.get(key);
      System.out.println(vo);
  }

```
