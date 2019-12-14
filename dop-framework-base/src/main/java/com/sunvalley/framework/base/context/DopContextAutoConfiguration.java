package com.sunvalley.framework.base.context;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 服务上下文配置
 *
 * @author dream.lu
 */
@Configuration
@EnableConfigurationProperties(DopContextProperties.class)
public class DopContextAutoConfiguration {

}
