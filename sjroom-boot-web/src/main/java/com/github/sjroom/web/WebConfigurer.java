package com.github.sjroom.web;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


/**
 * Spring MVC 配置
 */
@Slf4j
public class WebConfigurer extends WebMvcConfigurerAdapter {


    @Autowired
    JsonHttpMessageConverter jsonHttpMessageConverter;

    /**
     * json消息转换类
     * @return
     */
    @Bean
    public JsonHttpMessageConverter jsonHttpMessageConverter() {
        log.info("MvcConfigurer JsonHttpMessageConverter spring inited");
        return new JsonHttpMessageConverter();
    }

    /**
     * 消息返回时，统一处理
     * 1.有fastjson进行处理，日期格式化等等
     * 2.封装成公司的包装类commonResponse
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("MvcConfigurer configureMessageConverters started");
        converters.add(jsonHttpMessageConverter);
    }


    /**
     * 统一异常处理
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        log.info("MvcConfigurer configureHandlerExceptionResolvers started");
        log.debug("MvcConfigurer configureHandlerExceptionResolvers started2");
        exceptionResolvers.add(new JsonMappingExceptionResolver());
    }

}
