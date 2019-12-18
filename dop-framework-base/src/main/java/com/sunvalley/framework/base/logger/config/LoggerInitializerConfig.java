package com.sunvalley.framework.base.logger.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.net.URISyntaxException;
import java.net.URL;

/**
 * log4j2配置的定义key
 *
 * @author manson.zhou
 */
public class LoggerInitializerConfig {

	/**
	 * 日志目录变量名
	 */
	public static final String LOGGING_PATH = "LOGGING_PATH";
	/**
	 * Root 日志级别
	 */
	public static final String ROOT_LOG_LEVEL = "LOG_ROOT_LEVEL";
	/**
	 * 日志路径
	 */
	public static final String LOGGING_PATH_FILE = "logger/log4j2.xml";

	/**
	 * 刷新日志配置
	 */
	public static void refreshLogConfig(Class<?> clazz) {
		try {
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			ClassLoader classLoader = clazz.getClassLoader();
			URL url = classLoader.getResource(LOGGING_PATH_FILE);
			context.setConfigLocation(url.toURI());
			//重新初始化Log4j2的配置上下文
			context.reconfigure();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
