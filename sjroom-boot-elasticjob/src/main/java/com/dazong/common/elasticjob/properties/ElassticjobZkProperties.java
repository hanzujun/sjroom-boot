package com.dazong.common.elasticjob.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 贸易研发部：yanghui（think）
 * @since 2017年10月21日
 */
@Configuration
@ConfigurationProperties(prefix="elastic.job.zk")
@Data
public class ElassticjobZkProperties {
	
	private String zkServer;
	
	private String namespace;
	
	private int baseSleepTimeMilliseconds = 1000;
	
	private int maxSleepTimeMilliseconds = 3000;
	
	private int maxRetries = 3;
	
	private int sessionTimeoutMilliseconds = 60000;
	
	private int connectionTimeoutMilliseconds = 15000;
	
	private String digest;

	/**
	 * 是否程序启动后加载定时任务
	 */
	private boolean lazy = false;
}
