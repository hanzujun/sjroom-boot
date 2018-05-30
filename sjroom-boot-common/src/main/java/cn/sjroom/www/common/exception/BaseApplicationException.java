package com.github.sjroom.common.exception;


import com.github.sjroom.common.IResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常基类，扩展了异常码code 与 异常参数 args
 * 序列化坑说明，fastjson序列化exception,必须要有适合的构造函数，否则会向上遍历找继承类的构造函数,会找不到code的值设置方法
 *
 * @author wzy
 */
public abstract class BaseApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常错误码
     */
    protected final Integer code;
    /**
     * 异常信息的参数
     */
    protected final transient Object[] args;

    public BaseApplicationException(IResult status) {
        this(status.getCode(), status.getMessage());
    }

    public BaseApplicationException(String message, Object[] args) {
        this(ExceptionStatusEnum.ERROR.getCode(), ExceptionStatusEnum.ERROR.getMessage(message), null, args);
    }

    public BaseApplicationException(String message, Throwable cause, Object[] args) {
        this(ExceptionStatusEnum.ERROR.getCode(), ExceptionStatusEnum.ERROR.getMessage(message), cause, args);
    }

    public BaseApplicationException(Integer code, String message) {
        this(code, message, null, null);
    }

    public BaseApplicationException(Integer code, String message, Throwable cause) {
        this(code, message, cause, null);
    }

    public BaseApplicationException(Integer code, String message, Object[] args) {
        this(code, message, null, args);
    }

    public BaseApplicationException(Integer code, String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public Integer getCode() {
        return this.code;
    }

    /**
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", this.code);
        map.put("message", this.getMessage());
        return map;
    }

}
