package com.github.sjroom.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.github.sjroom.mybatis.annotation.TransactionEnhance;
import com.github.sjroom.mybatis.injector.TableBIdSqlInjector;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * MybatisPlus配置
 *
 * @author L.cm
 */
@Configuration
@PropertySource({
        "classpath:/dop-mybatis/mybatis.properties"
})
public class MybatisPlusConfig {

    @Bean
    public TransactionEnhance genSpringAopWrapper() {
        return new TransactionEnhance();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * sql 注入
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new TableBIdSqlInjector();
    }

    /**
     * SQL执行效率插件
     *
     * @return PerformanceInterceptor
     */
    @Configuration
    @Profile({"dev1", "dev2"})
    @ConditionalOnProperty(value = "mybatis-plus.sql-log.enable", matchIfMissing = true)
    public static class MybatisPlusSqlLog {

        @Bean
        public PerformanceInterceptor performanceInterceptor(Environment environment) {
            PerformanceInterceptor interceptor = new PerformanceInterceptor();
            interceptor.setMaxTime(30);
            interceptor.setWriteInLog(true);
            return interceptor;
        }
    }

    /**
     * IEnum 枚举配置
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new MybatisPlusCustomizers();
    }

    /**
     * MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(ObjectProvider<IMetaObjectDataSources> objectProvider) {
        IMetaObjectDataSources metaObjectDataSources = objectProvider.getIfAvailable(() -> new IMetaObjectDataSources() {
        });
        return new MybatisPlusMetaObjectHandler(metaObjectDataSources);
    }

    /**
     * 自定义配置
     */
    public static class MybatisPlusCustomizers implements ConfigurationCustomizer {
        @Override
        public void customize(org.apache.ibatis.session.Configuration configuration) {
            configuration.getLanguageRegistry().setDefaultDriverClass(MybatisPlusXmlLanguageDriver.class);
            //configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
        }
    }

}
