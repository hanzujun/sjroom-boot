package com.sunvalley.framework.cache.annotation;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * redis 缓存 失效注解
 *
 * @author dream.lu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@CacheEvict
@RedisMergedAnnotation
public @interface RedisCacheEvict {

    /**
     * Alias for {@link #cacheNames}.
     */
    @AliasFor(value = "value", annotation = CacheEvict.class)
    String[] value() default {};

    /**
     * Names of the caches to use for the cache eviction operation.
     * <p>Names may be used to determine the target cache (or caches), matching
     * the qualifier value or bean name of a specific bean definition.
     *
     * @see #value
     * @see CacheConfig#cacheNames
     * @since 4.2
     */
    @AliasFor(value = "cacheNames", annotation = CacheEvict.class)
    String[] cacheNames() default {};

    /**
     * Spring Expression Language (SpEL) expression for computing the key dynamically.
     * <p>Default is {@code ""}, meaning all method parameters are considered as a key,
     * unless a custom {@link #keyGenerator} has been set.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #result} for a reference to the result of the method invocation, which
     * can only be used if {@link #beforeInvocation()} is {@code false}. For supported
     * wrappers such as {@code Optional}, {@code #result} refers to the actual object,
     * not the wrapper</li>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     */
    @AliasFor(value = "key", annotation = CacheEvict.class)
    String key() default "";

    /**
     * Spring Expression Language (SpEL) expression used for making the cache
     * eviction operation conditional.
     * <p>Default is {@code ""}, meaning the cache eviction is always performed.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     */
    @AliasFor(value = "condition", annotation = CacheEvict.class)
    String condition() default "";

    /**
     * Whether all the entries inside the cache(s) are removed.
     * <p>By default, only the value under the associated key is removed.
     * <p>Note that setting this parameter to {@code true} and specifying a
     * {@link #key} is not allowed.
     */
    @AliasFor(value = "allEntries", annotation = CacheEvict.class)
    boolean allEntries() default false;

    /**
     * Whether the eviction should occur before the method is invoked.
     * <p>Setting this attribute to {@code true}, causes the eviction to
     * occur irrespective of the method outcome (i.e., whether it threw an
     * exception or not).
     * <p>Defaults to {@code false}, meaning that the cache eviction operation
     * will occur <em>after</em> the advised method is invoked successfully (i.e.,
     * only if the invocation did not throw an exception).
     */
    @AliasFor(value = "beforeInvocation", annotation = CacheEvict.class)
    boolean beforeInvocation() default false;

}
