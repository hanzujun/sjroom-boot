package com.github.sjroom.common.response;

import com.github.sjroom.common.IResult;
import com.github.sjroom.common.exception.ExceptionStatusEnum;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 * @author luobinwen
 */
@Data
public class JsonResponse<T> implements IResult {

    private static final long serialVersionUID = -4197769202915890604L;

    private int code = ExceptionStatusEnum.SUCCESS.getCode();
    private String message = ExceptionStatusEnum.SUCCESS.getMessage();
    private T data;

    public JsonResponse() {
    }

    public JsonResponse(T data) {
        this.data = data;
    }

    public JsonResponse(int code, String message) {
        this(code, message, null);
    }

    public JsonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
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
