package github.sjroom.common.util;

import github.sjroom.common.exception.BusinessException;
import github.sjroom.common.exception.FrameworkException;
import github.sjroom.common.code.IResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 断言工具类，用于检查传入的参数是否合法等功能
 *
 * @author manson.zhou
 * @date 2018/01/09
 */
public class AssertUtil {

    /**
     * 异常抛出
     *
     * @param rCode
     */
    public static void throwFail(IResultCode rCode) throws BusinessException {
        throwFail0(rCode, null);
    }

    /**
     * 断言api异常,可输入国际化参数
     *
     * @param expression
     * @param resultCode
     * @param i18Args
     */
    public static void throwOnFalse(boolean expression, IResultCode resultCode, Object... i18Args) throws BusinessException {
        throwOnFalse0(expression, resultCode, null, i18Args);
    }

    public static void throwFail(IResultCode rCode, Object... i18nArgs) throws BusinessException {
        throwFail0(rCode, null, i18nArgs);
    }

    public static void throwOnFalseWithData(boolean expression, IResultCode resultCode, DataPair... dataPairs) throws BusinessException {
        throwOnFalse0(expression, resultCode, dataPairs);
    }

    public static void throwOnFalseWithData(boolean expression, IResultCode resultCode, DataPair[] dataPairs, Object... i18Args) throws BusinessException {
        throwOnFalse0(expression, resultCode, dataPairs, i18Args);
    }

    public static void throwFailWithData(IResultCode resultCode, DataPair... dataPairs) throws BusinessException {
        throwFail0(resultCode, dataPairs);
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
        throwOnFalse0(expression,
                ResultCode.builder().code(code).msg(message).build(),
                null, i18Args);
    }

    public static void throwFail(String code, String message, Object... i18Args) {
        throwFail0(ResultCode.builder().code(code).msg(message).build(),
                null, i18Args);
    }

    /**
     * 断言api异常,全量参数
     *
     * @param expression
     * @param resultCode
     * @param dataPairs
     * @param i18Args
     * @throws BusinessException
     */
    private static void throwOnFalse0(boolean expression, IResultCode resultCode, DataPair[] dataPairs, Object... i18Args) throws BusinessException {
        if (!expression) {
            throwFail0(resultCode, dataPairs, i18Args);
        }
    }

    private static void throwFail0(IResultCode resultCode, DataPair[] dataPairs, Object... i18Args) throws BusinessException {
        BusinessException exception = new BusinessException(resultCode);
        exception.setI18Args(i18Args);
        if (!CollectionUtil.isEmpty(dataPairs)) {
            Map<String, Object> values = Stream.of(dataPairs)
                    .filter(v -> v != null && ObjectUtil.allNotNull(v.field, v.data))
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

    @Getter
    @Setter
    @Builder
    public static class ResultCode implements IResultCode {
        private String code;
        private String msg;
    }

}
