package com.sunvalley.framework.lock;

import com.sunvalley.framework.core.utils.NumberUtil;
import com.sunvalley.framework.lock.annotation.AnnotationDistributedLocker;
import com.sunvalley.framework.lock.annotation.RedissonAnnotationDistributedLocker;
import com.sunvalley.framework.lock.annotation.spel.DistributedLockerExpressionEvaluator;
import com.sunvalley.framework.lock.annotation.spel.DopExpressionEvaluator;
import com.sunvalley.framework.lock.locker.DistributedLockerClient;
import com.sunvalley.framework.lock.locker.RedissonDistributedLockerClient;
import lombok.Getter;
import lombok.Setter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnBean(RedissonClient.class)
@EnableConfigurationProperties(LockAutoConfiguration.LockProperties.class)
@AutoConfigureAfter({RedissonAutoConfiguration.class})
public class LockAutoConfiguration {

    @Autowired
    private LockProperties lockProperties;

    @Bean
    @ConditionalOnMissingBean
    public AnnotationDistributedLocker distributedLocker(RedissonClient redissonClient) {
        return new RedissonAnnotationDistributedLocker(redissonClient,
            NumberUtil.getDefaultIfNull(this.lockProperties.getDefaultLeaseTime(), LockerConstant.DEFAULT_LEASE_TIME),
            NumberUtil.getDefaultIfNull(this.lockProperties.getDefaultWaitTime(), LockerConstant.DEFAULT_WAIT_TIME));
    }

    @Bean
    @ConditionalOnMissingBean
    public DopExpressionEvaluator expressionEvaluator() {
        return new DistributedLockerExpressionEvaluator();
    }

    @Bean
    public DistributedLockerClient distributedLockerClient(RedissonClient redissonClient) {
        return new RedissonDistributedLockerClient(redissonClient,
            NumberUtil.getDefaultIfNull(this.lockProperties.getDefaultLeaseTime(), LockerConstant.DEFAULT_LEASE_TIME),
            NumberUtil.getDefaultIfNull(this.lockProperties.getDefaultWaitTime(), LockerConstant.DEFAULT_WAIT_TIME));
    }

    @Configuration
    @ConditionalOnClass({AnnotationDistributedLocker.class, DopExpressionEvaluator.class})
    @Import(DistributedLockerAspect.class)
    public static class LockAspectConfiguration {

    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "dop.lock")
    public static class LockProperties {
        private Integer defaultLeaseTime;
        private Integer defaultWaitTime;
    }
}
