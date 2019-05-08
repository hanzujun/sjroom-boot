package com.github.sjroom.common.response;

import com.github.sjroom.common.CommonStatus;
import com.github.sjroom.common.exception.ArgumetException;
import com.github.sjroom.common.exception.BusinessException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
        Integer code = CommonStatus.ILLEGAL_PARAM.getCode();
        String message = CommonStatus.ILLEGAL_PARAM.getMessage(msg);
        throw new ArgumetException(code, message);
    }

    /**
     * 抛出业务异常
     */
    public static void throwBusinessException(String msg) {
        Integer code = CommonStatus.FAIL.getCode();
        String message = CommonStatus.FAIL.getMessage(msg);
        throw new BusinessException(code, message);
    }


    //检查是否为手机号码
    public final static String PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";

    public static boolean checkPhone(String result) {
        return Pattern.compile(PHONE_PATTERN).matcher(result).matches();
    }

    public static void required(Object o) {
        if (ObjectUtils.isEmpty(o)) {
            log.warn("required error:{}", o);
            throw new ArgumetException(o.toString());
        }
    }

    public static void required(Object o, String filedName) {
        if (ObjectUtils.isEmpty(o)) {
            String errMsg = filedName + "不能为空";
            log.warn("required warn:{} ", errMsg);
            throw new ArgumetException(errMsg);
        }
    }

    public static void pattern(String parameter, String pattern) {
        if (!parameter.matches(pattern)) {
            throw new BusinessException(parameter + "匹配错误");
        }
    }

    public static void match(String parameter1, String parameter2) {
        required(parameter1);
        required(parameter2);
        if (!parameter1.equals(parameter2)) {
            throw new BusinessException(parameter2 + "匹配错误");
        }
    }

    public static void text(String parameter) {
        pattern(parameter, "^[_A-z0-9]{6,36}$");
    }

    public static void email(String parameter) {
        pattern(parameter, "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }

    public static void url(String parameter) {
        pattern(parameter, "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
    }

    public static void number(Integer number, Integer max, Integer min) {
        if (number > max || number < min) {
            throw new BusinessException(number + "最大与最小值错误");
        }
    }

    static void number(Double number, Double max, Double min) {
        if (number > max || number < min) {
            throw new BusinessException(number + "最大与最小值错误");
        }
    }

    public static void number(BigDecimal number, BigDecimal max, BigDecimal min) {
        if (number.compareTo(max) > 0 || number.compareTo(min) < 0) {
            throw new BusinessException(number + "最大与最小值错误");
        }
    }

    public static void number(Float number, Float max, Float min) {
        if (number > max || number < min) {
            throw new BusinessException(number + "最大与最小值错误");
        }
    }
}
