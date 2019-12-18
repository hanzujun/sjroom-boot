package github.sjroom.base.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.sjroom.core.utils2.UtilJson;
import github.sjroom.core.utils2.UtilValidation;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.validation.Validator;

/**
 * 服务上下文配置
 *
 * @author manson.zhou
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class DopContextConfiguration {

    @Bean
    @Primary
    public ObjectMapper contetObjectMapper() {
        return UtilJson.mapper;
    }


    @Bean
    public Validator validator() {
        return UtilValidation.validatorFast;
    }
}
