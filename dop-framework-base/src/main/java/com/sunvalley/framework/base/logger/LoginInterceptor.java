package com.sunvalley.framework.base.logger;//package com.sunvalley.framework.base.logger;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Configuration
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static String MDC_KEY_USER_NAME = "userName";
	private static String MDC_KEY_REQ_ID = "reqId";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		MDC.put(MDC_KEY_USER_NAME, "zhaozhou");
		MDC.put(MDC_KEY_REQ_ID, UUID.randomUUID().toString());
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		MDC.remove(MDC_KEY_USER_NAME);
		MDC.remove(MDC_KEY_REQ_ID);
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}
