package com.sunvalley.framework.base.logger.util;

import com.sunvalley.framework.base.logger.env.EnvLogLevel;
import org.apache.logging.log4j.Level;
import org.springframework.core.env.Environment;


/**
 * @Author: Simms.shi
 * @Date: 2019/11/5 18:04
 * @Desc: console level 控制器
 */
public abstract class ConsolePredicate {

	/**
	 * 在启动阶段用这个级别,防止关键日志丢失
	 *
	 * @param environment
	 * @param envLogLevel
	 * @return
	 */
	public static Level getAmityLevel(Environment environment, EnvLogLevel envLogLevel) {
		String consoleLevel = environment.getProperty(LoggerConstants.CONSOLE_LOG_LEVEL, envLogLevel.getConsole());
		Level toLevel = Level.toLevel(consoleLevel);
		if (toLevel.intLevel() <= Level.ERROR.intLevel()) {
			return Level.WARN;
		}
		return toLevel;
	}

	/**
	 * 启动完成后，按标准配置使用,避免控制台日志膨胀
	 * 如返回空级别,代表不需要reload
	 *
	 * @param environment
	 * @param envLogLevel
	 * @return
	 */
	public static Level getFinalLevel(Environment environment, EnvLogLevel envLogLevel) {
		String consoleLevel = environment.getProperty(LoggerConstants.CONSOLE_LOG_LEVEL, envLogLevel.getConsole());
		Level amityLevel = getAmityLevel(environment, envLogLevel);
		Level toLevel = Level.toLevel(consoleLevel);
		if (toLevel.compareTo(amityLevel) != 0) {
			return toLevel;
		}
		return null;
	}


}
