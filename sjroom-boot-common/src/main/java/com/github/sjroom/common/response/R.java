package com.github.sjroom.common.response;

import com.github.sjroom.common.code.CommonsCode;
import com.github.sjroom.common.code.IResultCode;
import com.github.sjroom.common.exception.BusinessException;
import com.github.sjroom.common.exception.FrameworkException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 返回数据
 */
@Slf4j
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 异常抛出
     *
     * @param rCode
     */
    public static void throwFail(IResultCode rCode) throws BusinessException {
        throw new BusinessException(rCode);
    }

    /**
     * 断言api异常,可输入国际化参数
     *
     * @param expression
     * @param resultCode
     * @param i18Args
     */
    public static void throwOnFalse(boolean expression, IResultCode resultCode, Object... i18Args) throws BusinessException {
        throwOnFalse(expression, resultCode, i18Args, null);
    }

    /**
     * 异常抛出
     *
     */
    public static void throwFail(IResultCode rCode, Object... i18nArgs) throws BusinessException {
        throw new BusinessException(rCode, i18nArgs);
    }

    public static void required(Object... i18nArgs) throws BusinessException {
        throw new BusinessException(CommonsCode.DATA_COMMON_ERROR_FAILED, i18nArgs);
    }

    public static void throwFail(Object... i18nArgs) throws BusinessException {
        throw new BusinessException(CommonsCode.DATA_COMMON_ERROR_FAILED, i18nArgs);
    }

    /**
     * 断言api异常，全量参数
     *
     * @param expression
     * @param resultCode
     * @param dataPairs  数据对，用于api输出，包含国际化
     * @throws BusinessException
     */
    public static void throwOnFalse(boolean expression, IResultCode resultCode, Object[] i18Args, DataPair... dataPairs) throws BusinessException {
        throwOnFalse0(expression, resultCode.getCode(), resultCode.getMsg(), i18Args, dataPairs);
    }

    public static void throwFail(IResultCode resultCode, Object[] i18Args, DataPair... dataPairs) throws BusinessException {
        throwFail0(resultCode.getCode(), resultCode.getMsg(), i18Args, dataPairs);
    }

    /**
     * 断言api异常,可输入国际化参数
     *
     * @param expression
     * @param code
     * @param message
     * @param i18Args    国际化参数
     * @throws BusinessException
     */
    public static void throwOnFalse(boolean expression, String code, String message, Object... i18Args) throws BusinessException {
        throwOnFalse0(expression, code, message, i18Args, null);
    }

    public static void throwFail(String code, String message, Object... i18Args) {
        throwFail0(code, message, i18Args, null);
    }

    /**
     * 断言api异常,全量参数
     *
     * @param expression
     * @param code
     * @param message
     * @throws BusinessException
     */
    public static void throwOnFalse0(boolean expression, String code, String message, Object[] i18Args, DataPair... dataPairs) throws BusinessException {
        if (!expression) {
            throwFail0(code, message, i18Args, dataPairs);
        }
    }

    public static void throwFail0(String code, String message, Object[] i18Args, DataPair... dataPairs) throws BusinessException {
        BusinessException exception = new BusinessException(code, message);
        exception.setI18nArgs(i18Args);
        if (!CollectionUtils.isEmpty(Arrays.asList(dataPairs))) {
            Map<String, Object> values = Stream.of(dataPairs)
                    .filter(v -> v != null && org.apache.commons.lang3.ObjectUtils.allNotNull(v.field, v.data))
                    .collect(Collectors.toMap(v -> v.field, v -> v.data));
            exception.setValues(values);
        }
        throw exception;
    }

    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new FrameworkException(message);
        }
    }

    @Getter
    @Setter
    @Builder
    public static class DataPair {
        private String field; // 输出的key
        private Object data; //输出的value
    }
}
