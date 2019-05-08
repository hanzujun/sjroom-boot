## 简单封装elasticjob，方便在业务系统使用（基于springboot）

## 使用说明

### 引入

* maven配置中引入jar

```xml
<dependency>
    <groupId>com.dazong.common</groupId>
    <artifactId>dz-common-elasticjob-starter</artifactId>
    <version>官方发布最新版本</version>
</dependency>
```
* disconf 配置中加入
```xml
## elastic job
elastic.job.zk.zkServer=172.16.21.12:2181
elastic.job.zk.namespace=trade-job
```

只要引入jar包就会开启定时任务

### 代码使用

```java
@EjTask(cron="0/2 * * * * ?")
public class YanghuiTestJob implements SimpleJob{
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("YanghuiTestJob");
    }
}
```

只要在需要定时执行的类上加入注解@EjTask即可

注解相关属性说明

* jobName：作业名称，默认为类名的简单名称
* cron：cron表达式，用于控制作业触发时间
* shardingTotalCount：作业分片总数，默认为1
* shardingItemParameters：分片序列号和参数用等号分隔，多个键值对用逗号分隔,分片序列号从0开始，不可大于或等于作业分片总数如：0=a,1=b,2=c
* jobParameter：作业自定义参数,作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业，例：每次获取的数据量、作业实例从数据库读取的主键等
* misfire：是否开启错过任务重新执行
* description：作业描述信息
* properties：key=value;key=value
* jobEvent：是否开启事件追踪
* isStreamingProcess：是否流式处理，DataflowJob时才有效
* overwrite：本地配置是否可覆盖注册中心配置，如果可覆盖，每次启动作业都以本地配置为准
* failover：是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
* jobListener：作业监听器
