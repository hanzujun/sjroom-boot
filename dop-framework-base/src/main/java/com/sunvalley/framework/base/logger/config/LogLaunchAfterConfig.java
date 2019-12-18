package com.sunvalley.framework.base.logger.config;

import com.sunvalley.framework.base.boot.DopStartEventListener;
import com.sunvalley.framework.base.logger.LogInitializer;
import com.sunvalley.framework.base.logger.contants.EnvLogLevel;
import com.sunvalley.framework.base.logger.contants.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;

import java.util.Properties;
import java.util.stream.Stream;

/**
 * 项目启动事件后，对console输出级别进行修订
 *
 * @author L.cm
 */
@Slf4j
@Configuration
@AutoConfigureAfter(DopStartEventListener.class)
public class LogLaunchAfterConfig {

	@Async
	@Order
	@EventListener(WebServerInitializedEvent.class)
	public void afterStart(WebServerInitializedEvent event) {
		Environment environment = event.getApplicationContext().getEnvironment();
		// 环境
		String[] profiles = environment.getActiveProfiles();
		// 激活的环境
		String activeProfile = Stream.of(profiles).findFirst().orElse("");
		// 日志级别规则
		EnvLogLevel envLogLevel = EnvLogLevel.of(activeProfile);
		Properties properties = System.getProperties();
		System.out.println("LoggerPostProcessor launch after log----->" + envLogLevel.getLaunchAfter());
		properties.setProperty(LogConstants.ROOT_LOG_LEVEL, envLogLevel.getLaunchAfter());
		// 刷新日志配置
		LogInitializer.refreshLogConfig(this.getClass());
	}
}
