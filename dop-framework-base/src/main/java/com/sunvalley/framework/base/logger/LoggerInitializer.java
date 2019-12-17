package com.sunvalley.framework.base.logger;

import com.sunvalley.framework.base.logger.env.EnvLogLevel;
import com.sunvalley.framework.base.logger.util.LoggerInitializerConfig;
import com.sunvalley.framework.core.utils.StringPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.URISyntaxException;
import java.net.URL;
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
		String activeProfile = Stream.of(profiles).findFirst().orElse(StringPool.EMPTY);
		// 日志级别规则
		EnvLogLevel envLogLevel = EnvLogLevel.of(activeProfile);
		// 读取系统配置的日志目录，默认为项目下 logs
		String logBase = environment.getProperty(LoggerInitializerConfig.LOGGING_PATH, "logs");
		Properties properties = System.getProperties();

		// 设置子线程读取MDC变量
		properties.setProperty("log4j2.isThreadContextMapInheritable", "true");
		// 服务名设置到 sys 变量方便日志 log4j2.xml 中读取
		properties.setProperty("spring.application.name", appName);

		// 设定 root日志级别 debug
		properties.setProperty(LoggerInitializerConfig.ROOT_LOG_LEVEL, envLogLevel.getConsole());
		System.out.println("LoggerInitializer amityLevel console log--->" + envLogLevel.getConsole());
		//日志文件配置
		properties.setProperty("logging.file", String.format("%s/%s/all.log", logBase, appName));
		properties.setProperty("logging.config", "classpath:" + LoggerInitializerConfig.LOGGING_PATH_FILE);
		// 刷新日志配置,防止初始化日志，没有加载到log file里面
		LoggerInitializerConfig.refreshLogConfig(this.getClass());
	}
}
