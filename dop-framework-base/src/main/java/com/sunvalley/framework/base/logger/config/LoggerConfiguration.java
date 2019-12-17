package com.sunvalley.framework.base.logger.config;

import com.sunvalley.framework.base.context.servlet.DopWebMvcConfigurer;
import com.sunvalley.framework.base.logger.mdc.MdcLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 只有api与rmi的链接，才需要过滤
 */
@Slf4j
@Configuration
public class LoggerConfiguration {
	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public FilterRegistrationBean mdcLogFilter() {
		FilterRegistrationBean<MdcLogFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new MdcLogFilter());
 		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.addUrlPatterns(DopWebMvcConfigurer.ApiMapping, DopWebMvcConfigurer.RmiMapping);
		return registrationBean;
	}
}
