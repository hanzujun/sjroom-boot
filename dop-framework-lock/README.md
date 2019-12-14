####  分布式锁

* yml配置
```yaml
dop:
  redisson:
    # 必输，redis模式single，sentinel，cluster
    mode: cluster
     # 必输，redis集群地址
    nodeAddress: redis://10.20.0.85:6379
    # 可选，配置实际的redis密码即可
    password: foobared
    # 可选，默认为ture
    enabled: true
    # 可选，默认为20
    poolSize: 300
    # 可选，默认为5
    idleSize: 20
    # 可选，默认为60000
    idleTimeout: 60000
    # 可选，默认为3000
    connectionTimeout: 10000
    # 可选，默认为10000
    timeout: 5000
  lock:
    # 可选，默认为100s
    defaultLeaseTime: 100
    # 可选，默认为30s
    defaultWaitTime: 30
```
* 注解方式使用

[@DistributedLocked](src/main/java/com/sunvalley/framework/lock/annotation/DistributedLocked.java)

| 属性 | 属性名  | 描述  | 默认值 |
|--------|--------|--------|--------|
|lockName|锁名|支持spel|—|
|waitTime|等待锁超时时间|单位:s|30|
|leaseTime|自动解锁时间|单位:s，自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放|100|
|ignoreException|忽略所有异常|boolean类型|flase|
|ignoreUnableToAcquiredLockException|忽略没有获取到锁的异常|boolean类型|true|

```java
    @DistributedLocked(lockName = "#userId")
    @Override
    public void testLock1(String userId) {
        System.out.println(userId);
    }
```
* API调用

[DistributedLockerClient](src/main/java/com/sunvalley/framework/lock/locker/DistributedLockerClient.java)

```java
    @Autowired
    private DistributedLockerClient lockerClient;
    
    @Override
    public void testLock2(String userId) {
        lockerClient.lock(userId);
        try {
            // ...业务代码
        } finally {
            lockerClient.unlock(userId);
        }   
    }
```
> 注意lock和unlock的执行需要在同一个线程内，加锁和解锁都会根据线程ID进行操作，跨线程会导致解锁失败

* 注意事项
> 包含事务的service或comp层慎用分布式锁，因为这样会先获取数据库连接，再来等待应用锁；可以使用一个不含事务的comp层方法加@DistributedLocked来调用含事务的service或comp