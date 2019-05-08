package com.github.sjroom.common.startup;

import org.springframework.context.ApplicationContext;

/**
 * @author huqichao
 * @date 2018-07-13 15:58
 */
public interface StartupListener {

    /**
     * 应用启动完成，端口监听后执行方法.
     * 当该方法抛出异常时，将会导致应用关闭
     * @param context spring context
     */
    void start(ApplicationContext context);

    /**
     * 检测该监听器执行完方式是否都成功
     * @param context spring context
     * @return 检测状态
     */
    boolean check(ApplicationContext context);

    /**
     * 执行顺序，越小越前执行
     * @return 执行顺序
     */
    int order();
}
