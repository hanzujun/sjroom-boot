package com.sunvalley.framework.core.utils;


import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author L.cm
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class CollectionUtil extends org.springframework.util.CollectionUtils {

    /**
     * Return {@code true} if the supplied Collection is not {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is not empty
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtil.isEmpty(collection);
    }

    /**
     * Return {@code true} if the supplied Map is not {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is not empty
     */
    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !CollectionUtil.isEmpty(map);
    }

    /**
     * Check whether the given Array contains the given element.
     *
     * @param array   the Array to check
     * @param element the element to look for
     * @param <T>     The generic tag
     * @return {@code true} if found, {@code false} else
     */
    public static <T> boolean contains(@Nullable T[] array, final T element) {
        if (array == null) {
            return false;
        }
        return Arrays.stream(array).anyMatch(x -> ObjectUtil.nullSafeEquals(x, element));
    }

    /**
     * Concatenates 2 arrays
     *
     * @param one   数组1
     * @param other 数组2
     * @return 新数组
     */
    public static String[] concat(String[] one, String[] other) {
        return concat(one, other, String.class);
    }

    /**
     * Concatenates 2 arrays
     *
     * @param one   数组1
     * @param other 数组2
     * @param clazz 数组类
     * @return 新数组
     */
    public static <T> T[] concat(T[] one, T[] other, Class<T> clazz) {
        T[] target = (T[]) Array.newInstance(clazz, one.length + other.length);
        System.arraycopy(one, 0, target, 0, one.length);
        System.arraycopy(other, 0, target, one.length, other.length);
        return target;
    }

    /**
     * 不可变 Set
     *
     * @param es  对象
     * @param <E> 泛型
     * @return 集合
     */
    @SafeVarargs
    public static <E> Set<E> ofImmutableSet(E... es) {
        Objects.requireNonNull(es, "args es is null.");
        return Arrays.stream(es).collect(Collectors.toSet());
    }

    /**
     * 不可变 List
     *
     * @param es  对象
     * @param <E> 泛型
     * @return 集合
     */
    @SafeVarargs
    public static <E> List<E> ofImmutableList(E... es) {
        Objects.requireNonNull(es, "args es is null.");
        return Arrays.stream(es).collect(Collectors.toList());
    }

}
