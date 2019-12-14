package com.sunvalley.framework.base.boot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库等密码加密配置
 *
 * @author dream.lu
 */
@Configuration
@EnableEncryptableProperties
public class EncryptAutoConfiguration {
}
