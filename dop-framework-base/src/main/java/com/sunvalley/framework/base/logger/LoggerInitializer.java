package com.sunvalley.framework.base.logger;

import com.sunvalley.framework.base.logger.env.EnvLogLevel;
import com.sunvalley.framework.base.logger.util.ConsolePredicate;
import com.sunvalley.framework.base.logger.util.LoggerConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Properties;
import java.util.stream.Stream;

/**
 * 应用日志处理
 *
 * @author dream.lu
 */
public class LoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		if (applicationContext.getParent() != null) {// 非root容器
			return;
		}
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		// 服务名
		String appName = environment.getProperty("spring.application.name");
		// 环境
		String[] profiles = environment.getActiveProfiles();
		// 激活的环境
		String activeProfile = Stream.of(profiles).findFirst().orElse("");
		// 日志级别规则
		EnvLogLevel envLogLevel = EnvLogLevel.of(activeProfile);
		// 读取系统配置的日志目录，默认为项目下 logs
		String logBase = environment.getProperty(LoggerConstants.LOG_PATH_ENV_KEY, "logs");
		Properties properties = System.getProperties();

//        properties.setProperty("logging.file", String.format("%s/%s/all.log", logBase, appName));
		// 服务名设置到 sys 变量方便日志 log4j2.xml 中读取
		properties.setProperty("spring.application.name", appName);

		// 设定 root 和 Console 日志级别
		// 兼容 spring boot 默认的 logging.level.root: debug，支持 bootstrap.yml 或 -Dlogging.level.root=debug
		String rootLevel = environment.getProperty("logging.level.root", envLogLevel.getRoot());
		properties.setProperty(LoggerConstants.ROOT_LOG_LEVEL_KEY, rootLevel);

		// 控制台日志级别，java -DLOG_CONSOLE_LEVEL=debug
//        String consoleLevel = environment.getProperty(LoggerConstants.CONSOLE_LOG_LEVEL_KEY, envLogLevel.getConsole());
		Level amityLevel = ConsolePredicate.getAmityLevel(environment, envLogLevel);
		System.out.println("LoggerInitializer.ConsoleLevelFilter.setAmityLevel----->" + amityLevel);
//		properties.setProperty(LoggerConstants.CONSOLE_LOG_LEVEL_VALUE, consoleLevel);
//		ConsoleLevelFilter.setLevel(amityLevel);
		String appendRef = environment.getProperty(LoggerConstants.LOG_APPEND_KEY, envLogLevel.getAppender());
		if (StringUtils.equalsIgnoreCase(appendRef, LoggerConstants.LOG_ELK_KEY)) {
			// Appender
			properties.setProperty(LoggerConstants.LOG_APPEND_VALUE, "elkFile");
			// 用于 spring boot admin 中展示日志
			properties.setProperty("logging.file", String.format("%s/%s/elk.log", logBase, appName));
		} else {
			// Appender
			properties.setProperty(LoggerConstants.LOG_APPEND_VALUE, "rewriteAllFile");
			// 用于 spring boot admin 中展示日志
			properties.setProperty("logging.file", String.format("%s/%s/all.log", logBase, appName));
		}
		properties.setProperty("logging.config", "classpath:logger/log4j2.xml");

		// 设置子线程读取MDC变量
		properties.setProperty("log4j2.isThreadContextMapInheritable", "true");
//        // 关闭 nacos 默认的 log 配置
//        properties.setProperty("nacos.logging.default.config.enabled", "false");
//        // 关闭 rocketmq 默认的 log 配置
//        properties.setProperty("rocketmq.client.log.loadconfig", "false");
	}
}
