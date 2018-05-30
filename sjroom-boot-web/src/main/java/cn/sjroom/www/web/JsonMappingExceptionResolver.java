package com.github.sjroom.web;

import com.github.sjroom.common.exception.BaseApplicationException;
import com.github.sjroom.common.exception.ExceptionStatusEnum;
import com.github.sjroom.common.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author luobinwen
 */
public class JsonMappingExceptionResolver extends SimpleMappingExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(JsonMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {
        logger.error("请求异常！请求地址{}", request.getRequestURL(), ex);
        // Expose ModelAndView for json response
        ModelAndView jsonmv = new ModelAndView(new JsonViewer());


        Map<String, Object> resultMap;
        if (ex instanceof BaseApplicationException) {
            resultMap = ((BaseApplicationException) ex).toMap();
        } else {
            JsonResponse resp = new JsonResponse(ExceptionStatusEnum.ERROR.getCode(), ex.getMessage());
            resultMap = resp.toMap();
        }

        logger.info("请求异常！结果：{}", resultMap);
        jsonmv.addAllObjects(resultMap);
        return jsonmv;
    }
}
