package github.sjroom.common.web;

import github.sjroom.common.logger.mdc.LogMdcInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @Auther: manson.zhou
 * @Date: 2019/07/29 18:47
 * @Version 1.8
 * @Description: springboot项目的基础  WebMvcConfigurer  实现
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AccessWebConfigurer implements WebMvcConfigurer {


    public final static String ApiMapping = "/api/**";
    public final static String RmiMapping = "/rmi/**";
    public final static Pattern ApiPattern = Pattern.compile("^\\/api\\/.*", Pattern.CASE_INSENSITIVE);
    public final static Pattern RmiPattern = Pattern.compile("^\\/rmi\\/.*", Pattern.CASE_INSENSITIVE);


    public static boolean isApiRequest(HttpServletRequest request) {
        return ApiPattern.matcher(request.getServletPath()).matches();
    }

    public static boolean isRmiRequest(HttpServletRequest request) {
        return RmiPattern.matcher(request.getServletPath()).matches();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ApiMapping);
    }

    @Autowired
    LogMdcInterceptorAdapter logMdcInterceptorAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration registrationBean = registry.addInterceptor(logMdcInterceptorAdapter);
        // 配置拦截的路径
        registrationBean.addPathPatterns(AccessWebConfigurer.ApiMapping, AccessWebConfigurer.RmiMapping);
        // 配置不拦截的路径
        registrationBean.excludePathPatterns("/static/**", "/templates/**");
    }
}
