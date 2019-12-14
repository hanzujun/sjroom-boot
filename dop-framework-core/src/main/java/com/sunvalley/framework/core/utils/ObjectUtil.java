package com.sunvalley.framework.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * 对象工具类
 *
 * @author L.cm
 */
@UtilityClass
public class ObjectUtil extends org.springframework.util.ObjectUtils {

    /**
     * 判断对象为null
     *
     * @param object 数组
     * @return 数组是否为空
     */
    public static boolean isNull(@Nullable Object object) {
        return object == null;
    }

    /**
     * 判断对象不为null
     *
     * @param object 数组
     * @return 数组是否为空
     */
    public static boolean isNotNull(@Nullable Object object) {
        return object != null;
    }

    /**
     * 是否为 true
     *
     * @param bool boolean
     * @return boolean
     */
    public static boolean isTrue(boolean bool) {
        return bool;
    }

    /**
     * 是否为 true
     *
     * @param bool Boolean
     * @return boolean
     */
    public static boolean isTrue(@Nullable Boolean bool) {
        return Optional.ofNullable(bool).orElse(Boolean.FALSE);
    }

    /**
     * 是否为 false
     *
     * @param bool Boolean
     * @return boolean
     */
    public static boolean isFalse(boolean bool) {
        return !ObjectUtil.isTrue(bool);
    }

    /**
     * 是否为 false
     *
     * @param bool Boolean
     * @return boolean
     */
    public static boolean isFalse(@Nullable Boolean bool) {
        return !ObjectUtil.isTrue(bool);
    }

    /**
     * 判断数组不为空
     *
     * @param array 数组
     * @return 数组是否为空
     */
    public static boolean isNotEmpty(@Nullable Object[] array) {
        return !ObjectUtil.isEmpty(array);
    }

    /**
     * 判断对象不为空
     *
     * @param obj 数组
     * @return 数组是否为空
     */
    public static boolean isNotEmpty(@Nullable Object obj) {
        return !ObjectUtil.isEmpty(obj);
    }
}
