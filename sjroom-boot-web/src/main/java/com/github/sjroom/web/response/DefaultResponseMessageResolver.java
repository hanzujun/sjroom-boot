package com.github.sjroom.web.response;


import com.github.sjroom.common.code.IResultCode;
import com.github.sjroom.common.exception.FrameworkException;
import com.github.sjroom.common.utils2.UtilJson;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Katrel.Zhou
 * @date 2019/5/28 9:49
 * @desc 手动对response进行resolver, 适用于鉴权等服务
 */
@AllArgsConstructor
public class DefaultResponseMessageResolver implements ResponseMessageResolver {

    private final static String STATE_CODE = "stateCode";
    private final static String STATE_MSG = "stateMsg";

    @Override
    public void successResolve(HttpServletRequest request, HttpServletResponse response, Object data) {
        resolve(request, response, data);
    }

    @Override
    public void failResolve(HttpServletRequest request, HttpServletResponse response, String code, String message,
                            Object... args) {
        Map<String, Object> result = new HashMap<>();
        result.put(STATE_CODE, code);
        result.put(STATE_MSG, new MessageFormat(message).format(args));
        resolve(request, response, result);
    }

    @Override
    public void failResolve(HttpServletRequest request, HttpServletResponse response, IResultCode resultCode,
                            Object... args) {
        failResolve(request, response, resultCode.getCode(), resultCode.getMsg(), args);
    }

    private void resolve(HttpServletRequest request, HttpServletResponse response, Object data) {
        try (PrintWriter writer = response.getWriter()) {
            UtilJson.writeValue(writer, data);
            writer.flush();
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }
}

