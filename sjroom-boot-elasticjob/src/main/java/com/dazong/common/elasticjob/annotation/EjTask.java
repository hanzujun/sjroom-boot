package com.dazong.common.elasticjob.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 贸易研发部：yanghui
 * @since 2017年10月22日
 */
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EjTask {
	/**
	 * 作业名称，默认为类名的简单名称
	 */
	String jobName() default "";
	/**
	 * cron表达式，用于控制作业触发时间
	 */
    String cron();
    /**
	 * 作业分片总数
	 */
    int shardingTotalCount() default 1;
    /**
     * 分片序列号和参数用等号分隔，多个键值对用逗号分隔,分片序列号从0开始，不可大于或等于作业分片总数
	 * 如：0=a,1=b,2=c
     */
    String shardingItemParameters() default "";
    /**
     * 作业自定义参数,作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
	 * 例：每次获取的数据量、作业实例从数据库读取的主键等
     */
    String jobParameter() default "";
    /**
     * 是否开启错过任务重新执行
     */
    boolean misfire() default true;
    /**
     * 作业描述信息
     */
    String description() default "";
    /**
     * key=value;key=value
     */
    String properties() default "";
    /**
     * 是否开启事件追踪
     */
    boolean jobEvent() default false;
    /**
     * 是否流式处理，DataflowJob时才有效
     */
    boolean isStreamingProcess() default false;//
    /**
     * 本地配置是否可覆盖注册中心配置，如果可覆盖，每次启动作业都以本地配置为准
     */
    boolean overwrite() default true;
    /**
     * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
     */
    boolean failover() default true;
    /**
     * 作业监听器
     */
    Class<? extends ElasticJobListener>[] jobListener() default ElasticJobListener.class;
}
