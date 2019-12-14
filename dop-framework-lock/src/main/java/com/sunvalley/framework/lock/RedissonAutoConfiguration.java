package com.sunvalley.framework.lock;

import com.sunvalley.framework.core.utils.NumberUtil;
import com.sunvalley.framework.core.utils.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.classreading.MethodMetadataReadingVisitor;

/**
 * redisson configuration
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    @ConditionalOnMissingBean(Config.class)
    @Conditional(RedissonServerModeCondition.class)
    public Config singleConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress(LockerConstant.REDISSON_SINGLE_PREFIX + redissonProperties.getAddress());
        if (StringUtil.isNotBlank(redissonProperties.getPassword())) {
            config.useSingleServer().setPassword(redissonProperties.getPassword());
        }
        config.useSingleServer().setDatabase(NumberUtil.getDefaultIfNull(redissonProperties.getDatabase(), LockerConstant.DEFAULT_DATABASE));
        config.useSingleServer().setConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useSingleServer().setConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useSingleServer().setIdleConnectionTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_IDLE_TIMEOUT));
        config.useSingleServer().setConnectTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_CONNECTION_TIMEOUT));
        config.useSingleServer().setTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getTimeout(), LockerConstant.DEFAULT_TIMEOUT));
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    @Conditional(RedissonServerModeCondition.class)
    public Config masterSlaveConfig() {
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress(redissonProperties.getMasterAddress());

        config.useMasterSlaveServers().addSlaveAddress(redissonProperties.getSlaveAddress());
        if (StringUtil.isNotBlank(redissonProperties.getPassword())) {
            config.useMasterSlaveServers().setPassword(redissonProperties.getPassword());
        }
        config.useMasterSlaveServers().setDatabase(NumberUtil.getDefaultIfNull(redissonProperties.getDatabase(), LockerConstant.DEFAULT_DATABASE));
        config.useMasterSlaveServers().setMasterConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useMasterSlaveServers().setMasterConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useMasterSlaveServers().setSlaveConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useMasterSlaveServers().setSlaveConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useMasterSlaveServers().setIdleConnectionTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_IDLE_TIMEOUT));
        config.useMasterSlaveServers().setConnectTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_CONNECTION_TIMEOUT));
        config.useMasterSlaveServers().setTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getTimeout(), LockerConstant.DEFAULT_TIMEOUT));
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    @Conditional(RedissonServerModeCondition.class)
    public Config sentinelConfig() {
        Config config = new Config();
        config.useSentinelServers().setMasterName(redissonProperties.getMasterName());
        config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddress());
        if (StringUtil.isNotBlank(redissonProperties.getPassword())) {
            config.useSentinelServers().setPassword(redissonProperties.getPassword());
        }
        config.useSentinelServers().setDatabase(NumberUtil.getDefaultIfNull(redissonProperties.getDatabase(), LockerConstant.DEFAULT_DATABASE));
        config.useSentinelServers().setMasterConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useSentinelServers().setMasterConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useSentinelServers().setSlaveConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useSentinelServers().setSlaveConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useSentinelServers().setIdleConnectionTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_IDLE_TIMEOUT));
        config.useSentinelServers().setConnectTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_CONNECTION_TIMEOUT));
        config.useSentinelServers().setTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getTimeout(), LockerConstant.DEFAULT_TIMEOUT));
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    @Conditional(RedissonServerModeCondition.class)
    public Config clusterConfig() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress(redissonProperties.getNodeAddress());
        if (StringUtil.isNotBlank(redissonProperties.getPassword())) {
            config.useClusterServers().setPassword(redissonProperties.getPassword());
        }
        config.useClusterServers().setMasterConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useClusterServers().setMasterConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useClusterServers().setSlaveConnectionPoolSize(NumberUtil.getDefaultIfNull(redissonProperties.getPoolSize(), LockerConstant.DEFAULT_POOL_SIZE));
        config.useClusterServers().setSlaveConnectionMinimumIdleSize(NumberUtil.getDefaultIfNull(redissonProperties.getIdleSize(), LockerConstant.DEFAULT_IDLE_SIZE));
        config.useClusterServers().setIdleConnectionTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_IDLE_TIMEOUT));
        config.useClusterServers().setConnectTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getConnectionTimeout(), LockerConstant.DEFAULT_CONNECTION_TIMEOUT));
        config.useClusterServers().setTimeout(NumberUtil.getDefaultIfNull(redissonProperties.getTimeout(), LockerConstant.DEFAULT_TIMEOUT));
        return config;
    }

    @Bean
    @ConditionalOnBean(Config.class)
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

    public static class RedissonServerModeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String mode = context.getEnvironment().getProperty("dop.redisson.mode");
            if (StringUtil.isBlank(mode)) {
                return false;
            }
            String methodName = ((MethodMetadataReadingVisitor) metadata).getMethodName().toLowerCase();
            return methodName.startsWith(mode.toLowerCase());
        }

    }
}
