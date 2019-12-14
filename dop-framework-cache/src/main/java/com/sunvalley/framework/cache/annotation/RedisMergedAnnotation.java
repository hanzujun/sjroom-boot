package com.sunvalley.framework.cache.annotation;

import java.lang.annotation.*;

/**
 * 标记为redis cache
 *
 * @author dream.lu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisMergedAnnotation {
}
