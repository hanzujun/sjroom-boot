package github.sjroom.common.web.config;

import github.sjroom.common.util.JsonUtil;
import github.sjroom.common.web.AccessWebAspect;
import github.sjroom.common.web.AccessWebConfigurer;
import github.sjroom.common.web.WebExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 *
 */
@Slf4j
@Configuration
public class WebConfig {
    /**
     * jackson 转换器
     *
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(JsonUtil.getInstance());
    }

    /**
     * Spring boot 控制器 请求日志，方便代码调试
     *
     * @return
     */
    @Bean
    public AccessWebAspect accessWebAspect() {
        return new AccessWebAspect();
    }

    /**
     * Spring boot 控制器 请求日志，方便代码调试
     *
     * @return
     */
    @Bean
    public AccessWebConfigurer accessWebConfigurer() {
        return new AccessWebConfigurer();
    }


    /**
     * mvc 基础的异常拦截和处理器
     *
     * @return
     */
    @Bean
    public WebExceptionAdvice webExceptionAdvice() {
        return new WebExceptionAdvice();
    }

    @Bean
    public StartEventListenerConfig startEventListenerConfig() {
        return new StartEventListenerConfig();
    }

    @Bean
    public StartEventListenerAfterConfig startEventListenerAfterConfig() {
        return new StartEventListenerAfterConfig();
    }
}
