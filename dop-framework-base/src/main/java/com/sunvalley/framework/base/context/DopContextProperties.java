package com.sunvalley.framework.base.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 上下文配置
 *
 * @author manson.zhou
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("dop.context")
public class DopContextProperties {

    /**
     * RestTemplate 和 Fegin 透传到下层的 Headers 名称列表
     */
    private List<String> allowedHeaders = new ArrayList<>();

}
