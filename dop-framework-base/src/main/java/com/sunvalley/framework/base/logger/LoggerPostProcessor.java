package com.sunvalley.framework.base.logger;

import com.sunvalley.framework.base.boot.DopStartEventListener;
import com.sunvalley.framework.base.logger.env.EnvLogLevel;
import com.sunvalley.framework.base.logger.util.LoggerInitializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;

import java.net.URISyntaxException;
import java.net.URL;
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
public class LoggerPostProcessor {

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
		System.out.println("LoggerPostProcessor.afterStart console log----->" + envLogLevel.getConsole());
		System.out.println("LoggerPostProcessor.afterStart root log----->" + envLogLevel.getRoot());
		properties.setProperty(LoggerInitializerConfig.ROOT_LOG_LEVEL, envLogLevel.getRoot());
		// 刷新日志配置
		LoggerInitializerConfig.refreshLogConfig(this.getClass());
	}
}
