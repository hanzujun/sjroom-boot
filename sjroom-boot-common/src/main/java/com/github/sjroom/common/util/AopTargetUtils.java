package com.github.sjroom.common.util;

import com.github.sjroom.common.response.R;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

/**
 * 基于Spring AOP获取目标对象.
 *
 * @author huqichao
 */
@Slf4j
public final class AopTargetUtils {

  private AopTargetUtils() {
  }

  /**
   * 获取目标对象.
   *
   * @param proxy 代理对象
   * @return 目标对象
   */
  public static Object getTarget(final Object proxy) {
    if (!AopUtils.isAopProxy(proxy)) {
      return proxy;
    }
    if (AopUtils.isJdkDynamicProxy(proxy)) {
      return getProxyTargetObject(proxy, "h");
    } else {
      return getProxyTargetObject(proxy, "CGLIB$CALLBACK_0");
    }
  }

  private static Object getProxyTargetObject(final Object proxy, final String proxyType) {
    Field h;
    try {
      h = proxy.getClass().getSuperclass().getDeclaredField(proxyType);
    } catch (final NoSuchFieldException ex) {
      log.error("getProxyTargetObject error {}:", ex.getMessage());
      return getProxyTargetObjectForCglibAndSpring4(proxy);
    }
    h.setAccessible(true);
    try {
      return getTargetObject(h.get(proxy));
    } catch (final IllegalAccessException ex) {
      R.throwBusinessException("getProxyTargetObject error:" + ex.getMessage());
    }
    return null;
  }

  private static Object getProxyTargetObjectForCglibAndSpring4(final Object proxy) {
    Field h;
    try {
      h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
      h.setAccessible(true);
      Object dynamicAdvisedInterceptor = h.get(proxy);
      Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
      advised.setAccessible(true);
      return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource()
          .getTarget();
      // CHECKSTYLE:OFF
    } catch (final Exception ex) {
      // CHECKSTYLE:ON
      R.throwBusinessException("getProxyTargetObjectForCglibAndSpring4 error:" + ex.getMessage());
    }
    return null;
  }

  private static Object getTargetObject(final Object object) {
    try {
      Field advised = object.getClass().getDeclaredField("advised");
      advised.setAccessible(true);
      return ((AdvisedSupport) advised.get(object)).getTargetSource().getTarget();
      // CHECKSTYLE:OFF
    } catch (final Exception ex) {
      // CHECKSTYLE:ON
      R.throwBusinessException("getTargetObject error:" + ex.getMessage());
    }
    return null;
  }
}
