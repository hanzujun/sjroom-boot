package com.sunvalley.framework.base.logger.env;

import com.sunvalley.framework.base.logger.util.LoggerConstants;
import com.sunvalley.framework.core.context.LogConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 环境对用的日志级别
 *
 * @author dream.lu
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum EnvLogLevel {
	/**
	 * 各环境日志配置
	 */
	dev1("INFO", "INFO"),
	dev2("INFO", "WARN"),
	test("INFO", "WARN"),
	ptest("INFO", "OFF"),
	pro("INFO", "OFF", LoggerConstants.LOG_ELK_KEY);

	/**
	 * root 日志级别
	 */
	@NonNull
	private String root;
	/**
	 * 控制台日志级别
	 */
	@NonNull
	private String console;


	private String appender = "all"; // elk

	/**
	 * 环境与日志级别关联
	 *
	 * @param env 环境
	 * @return 日志级别
	 */
	public static EnvLogLevel of(String env) {
		EnvLogLevel[] logLevels = EnvLogLevel.values();
		for (EnvLogLevel logLevel : logLevels) {
			if (logLevel.name().equals(env.toLowerCase())) {
				return logLevel;
			}
		}
		return EnvLogLevel.dev1;
	}
}
