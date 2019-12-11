package com.github.sjroom.autoconfig;


import com.github.sjroom.cache.core.impl.LocalCacheHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author DanielLi
 * @description Local Cache自动化配置,目前还没实现,根据未来需要再实现
 */
@Configuration
@ImportResource("classpath:/META-INF/dz-common-cache.xml")
@ConditionalOnClass({LocalCacheHandler.class})
public class LocalCacheAutoConfigure{
}
