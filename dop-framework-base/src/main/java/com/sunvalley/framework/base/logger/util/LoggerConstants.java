package com.sunvalley.framework.base.logger.util;

/**
 * log4j2配置的定义key
 *
 * @author dream.lu
 */
public interface LoggerConstants {

    /**
     * 日志目录变量名
     */
    String LOG_PATH_ENV_KEY = "LOGGING_PATH";
    /**
     * 控制台日志级别
     */
    String CONSOLE_LOG_LEVEL = "LOG_CONSOLE_LEVEL";
	String CONSOLE_LOG_LEVEL_OFF = "OFF";
    /**
     * Root 日志级别
     */
    String ROOT_LOG_LEVEL_KEY = "LOG_ROOT_LEVEL";

	/**
	 * 集中输出的append.key 表达
	 */
	String LOG_APPEND_KEY = "LOG_APPEND_KEY";
	/**
	 * 集中输出的append.ref
	 */
	String LOG_APPEND_VALUE =  "LOG_APPEND_VALUE";

	/**
	 * 集中输出的append.ref
	 */
	String LOG_ELK_KEY = "elk";
}
