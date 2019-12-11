package com.github.sjroom.web.response;

/**
 * @Author: Simms.shi
 * @Date: 2019/7/29 12:01
 * @Desc: ****
 */


import com.github.sjroom.common.code.IResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 响应报文解析器
 *
 * @author Katrel.Zhou
 * @date 2019/5/28 9:43
 */
public interface ResponseMessageResolver {

    /**
     * 成功返回
     */
    void successResolve(HttpServletRequest request, HttpServletResponse response, Object data);

    /**
     * 失败返回
     */
    void failResolve(HttpServletRequest request, HttpServletResponse response,
                     String code, String message, Object... args);

    /**
     * 失败返回
     */
    void failResolve(HttpServletRequest request, HttpServletResponse response,
                     IResultCode resultCode, Object... args);

}
