package github.sjroom.common.logger.config;

import github.sjroom.common.logger.mdc.LogMdcInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Slf4j
@Configuration
public class LogConfig {

    @Bean
    public LogMdcInterceptorAdapter logMdcInterceptorAdapter() {
        return new LogMdcInterceptorAdapter();
    }


}
