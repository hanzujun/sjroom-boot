package com.github.sjroom.util;

import com.github.sjroom.common.exception.ArgumetException;
import com.github.sjroom.common.exception.BusinessException;
import com.github.sjroom.common.exception.ExceptionStatusEnum;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 断言工具类，用于检查传入的参数是否合法等功能
 *
 * @author zhouwei
 * @date 2018/01/09
 */
public class AssertUtil {

    public static void notNull(Object o) {
        notNull(o, "null");
    }

    public static void notNull(Object o, String msg) {
        if (o == null) {
            throwArgumetException(msg);
        }
    }
    public static void notBlank(String field, String fieldName) {
        if (StringUtils.isEmpty(field)) {
            throwArgumetException(fieldName);
        }
    }

    public static void notEmpty(Object o) {
        notEmpty(o, "filedName");
    }

    public static void notEmpty(Object o, String filedName) {
        if (ObjectUtils.isEmpty(o)) {
            throwArgumetException(filedName);
        }
    }

    /**
     * 抛出参数异常
     *
     * @param msg 消息
     */
    public static void throwArgumetException(String msg) {
        Integer code = ExceptionStatusEnum.ILLEGAL_PARAM.getCode();
        String message = ExceptionStatusEnum.ILLEGAL_PARAM.getMessage(msg);
        throw new ArgumetException(code, message);
    }

    /**
     * 抛出业务异常
     */
    public static void throwBusinessException(String msg) {
        Integer code = ExceptionStatusEnum.FAIL.getCode();
        String message = ExceptionStatusEnum.FAIL.getMessage(msg);
        throw new BusinessException(code, message);
    }

}
