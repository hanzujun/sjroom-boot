package com.sunvalley.framework.core.common;

import com.sunvalley.framework.core.utils2.UtilJson;
import feign.Feign;
import feign.Feign.Builder;
import feign.Request.Options;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 通用的feign构建方法
 * 可用于构建 spring bean 和普通的client
 * </pre>
 * #org.springframework.cloud.openfeign.FeignClientsRegistrar
 * #registerFeignClient(org.springframework.beans.factory.support.BeanDefinitionRegistry,
 * #org.springframework.core.type.AnnotationMetadata, java.util.Map)
 *
 * @param <T>
 * @author smj
 */
@Slf4j
public class FeignServiceFacade<T>  {

    // 对端的target、
    private String endpoint;

    // 传入class
    private Class<T> clazz;

    private Builder builder;

    /**
     * step1
     * @param endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * step2
     * @param clazz
     */
    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * step3
     * @return
     */
    public T getObject() {
        try {
            if (builder == null) {
                log.info("FeignService:{},{} use default builder", clazz, endpoint);
                builder = getDefaultBuilder();
            }
            T feign = builder.target(clazz, endpoint);
            return feign;
        } catch (Exception e) {
            log.error("FeignServiceFacade.getObject error: ", e);
            throw new IllegalStateException("FeignServiceFacade.getObject error:", e);
        }
    }






    /**
     * 支持对fegin行为进行定制
     * 超时配置
     * 代理配置
     * 报文协议
     * header等
     *
     * @param builder 详见{@linkplain Builder}
     */
    public void setBuilder(Builder builder) {
        this.builder = builder;
    }


    public static Builder getDefaultBuilder() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(UtilJson.mapper))//
                .decoder(new JacksonDecoder(UtilJson.mapper))
                .options(new Options(2000, 6000));
    }


}
