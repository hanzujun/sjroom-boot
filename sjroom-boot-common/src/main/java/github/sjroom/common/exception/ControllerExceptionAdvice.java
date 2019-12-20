package github.sjroom.common.exception;

import github.sjroom.common.constant.CommonStatusEnum;
import github.sjroom.common.constant.IResultConstants;
import github.sjroom.common.context.ContextConstants;
import github.sjroom.common.logger.LogInitializer;
import github.sjroom.common.logger.contants.LogConstants;
import github.sjroom.common.util.CollectionUtil;
import github.sjroom.common.util.Exceptions;
import github.sjroom.common.util.ObjectUtil;
import github.sjroom.common.util.StringPool;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * mvc 基础的异常拦截和处理器
 */
@Slf4j
@ToString
@Configuration
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> r = new HashMap<>();
        // xReqId
        Optional.ofNullable(ThreadContext.get(LogConstants.MDC_REQUEST_ID_KEY)).ifPresent(v -> r.put(LogConstants.MDC_REQUEST_ID_KEY, v));
        r.put(IResultConstants.MSG_DETAIL, ex.getMessage());
        r.put(IResultConstants.TIME_STAMP, new Date());

        CommonStatusEnum commonStatusEnum;
        // 业务异常
        if (ex instanceof BusinessException) {
            BusinessException aeEx = (BusinessException) ex;
            argsBusinessExceptionCovert(aeEx, r);
            // 业务参数异常 | 验证器异常
        } else if (ex instanceof MethodArgumentNotValidException
                || ex instanceof IllegalArgumentException
                || ex instanceof ValidationException) {
            commonStatusEnum = CommonStatusEnum.ILLEGAL_PARAM;
            r.put(IResultConstants.CODE, commonStatusEnum.getCode());
            r.put(IResultConstants.MSG, commonStatusEnum.getMessage());
        } else {
            //系统异常
            commonStatusEnum = CommonStatusEnum.FAIL;
            r.put(IResultConstants.CODE, commonStatusEnum.getCode());
            r.put(IResultConstants.MSG, commonStatusEnum.getMessage());
        }

        this.fillDebug(request, ex, r);
        ex.printStackTrace();
        return r;
    }


    /**
     * 处理异常信息,入参置换
     */
    protected void argsBusinessExceptionCovert(BusinessException ex, Map<String, Object> r) {
        Optional.ofNullable(ex.getValues())
                .filter(vs -> !CollectionUtil.isEmpty(vs))
                .ifPresent(vs -> r.putAll(vs));
        Object[] i18Args = (ex).getI18Args();
        String msg = String.valueOf(r.getOrDefault(IResultConstants.MSG_DETAIL, StringPool.EMPTY));
        MessageFormat format = new MessageFormat(msg);
        r.put(IResultConstants.MSG, format.format(i18Args));
        r.put(IResultConstants.CODE, ex.getCode());

        r.remove(IResultConstants.MSG_DETAIL);
    }

    /**
     * 当请求头中 x-debug 时,则直接打印出堆栈信息
     *
     * @param request
     * @param ex
     * @param r       返回结果
     */
    protected void fillDebug(HttpServletRequest request, Exception ex, Map<String, Object> r) {
        if (ObjectUtil.isNotEmpty(request.getHeader(ContextConstants.X_DEBUG))
                && "echo".equals(request.getHeader(ContextConstants.X_DEBUG))) {
            r.put(IResultConstants.STACK_TRACE, Exceptions.getStackTraceAsString(ex));
        }
    }
}
