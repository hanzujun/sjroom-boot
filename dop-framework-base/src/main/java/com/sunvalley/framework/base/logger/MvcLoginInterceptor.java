package com.sunvalley.framework.base.logger;//package com.sunvalley.framework.base.logger;
//
//import com.sunvalley.framework.base.context.servlet.DopWebMvcConfigurer;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MvcLoginInterceptor implements WebMvcConfigurer {
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 注册拦截器
//		InterceptorRegistration registrationBean = registry.addInterceptor(new LoginInterceptor());
//		// 配置拦截的路径
//		registrationBean.addPathPatterns(DopWebMvcConfigurer.ApiMapping, DopWebMvcConfigurer.RmiMapping);
//		// 配置不拦截的路径
//		registrationBean.excludePathPatterns("/static/**", "/templates/**");
//	}
//}
