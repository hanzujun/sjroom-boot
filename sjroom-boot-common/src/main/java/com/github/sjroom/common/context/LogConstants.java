package com.github.sjroom.common.context;

/**
 * 日志 常量值
 *
 * @author dream.lu
 */
public interface LogConstants {
    /**
     * MDC request id key
     */
    String MDC_REQUEST_ID_KEY = "xReqId";
    /**
     * 日志目录变量名
     */
    String LOG_PATH_ENV_KEY = "LOGGING_PATH";
    /**
     * 控制台日志级别
     */
    String CONSOLE_LOG_LEVEL_KEY = "LOG_CONSOLE_LEVEL";
    /**
     * Root 日志级别
     */
    String ROOT_LOG_LEVEL_KEY = "LOG_ROOT_LEVEL";
}
