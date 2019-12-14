package com.sunvalley.framework.lock;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @Author: Katrel.Zhou
 * @Date: 2019/5/16 10:34
 * @Version 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dop.redisson")
public class RedissonProperties {
    /**
     * 是否开启：默认为：true，便于生成配置提示。
     */
    private Boolean enabled = Boolean.TRUE;
    private String mode;
    private String address;
    private String password;
    private Integer database;
    private Integer poolSize;
    private Integer idleSize;
    private Integer idleTimeout;
    private Integer connectionTimeout;
    private Integer timeout;
    private String masterAddress;
    private String[] slaveAddress;
    private String masterName;
    private String[] sentinelAddress;
    private String[] nodeAddress;
}
