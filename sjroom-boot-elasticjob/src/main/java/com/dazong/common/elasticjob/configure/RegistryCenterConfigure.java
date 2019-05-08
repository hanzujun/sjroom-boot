package com.dazong.common.elasticjob.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dazong.common.elasticjob.properties.ElassticjobZkProperties;
/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 贸易研发部：yanghui
 * @since 2018年2月1日
 */
@Configuration
@Import(ElassticjobZkProperties.class)
public class RegistryCenterConfigure {
	
	@Value("${zk.host:}")
	private String zkServer;
	
	@Bean
	public CoordinatorRegistryCenter registryCenter(ElassticjobZkProperties elassticjobProperties){
		System.err.println(elassticjobProperties);
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(
				StringUtils.isEmpty(this.zkServer) ? elassticjobProperties.getZkServer() : this.zkServer, 
				elassticjobProperties.getNamespace());
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(elassticjobProperties.getBaseSleepTimeMilliseconds());
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(elassticjobProperties.getMaxSleepTimeMilliseconds());
		zookeeperConfiguration.setMaxRetries(elassticjobProperties.getMaxRetries());
		zookeeperConfiguration.setSessionTimeoutMilliseconds(elassticjobProperties.getSessionTimeoutMilliseconds());
		zookeeperConfiguration.setConnectionTimeoutMilliseconds(elassticjobProperties.getConnectionTimeoutMilliseconds());
		if(!StringUtils.isEmpty(elassticjobProperties.getDigest())){
			zookeeperConfiguration.setDigest(elassticjobProperties.getDigest());
		}
		CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
	}
}
