package github.sjroom.common.logger.mdc;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import github.sjroom.common.context.ContextConstants;
import github.sjroom.common.logger.contants.LogConstants;
import github.sjroom.common.util.StringUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 给每个api请求,加上request ID
 */
public class LogMdcInterceptorAdapter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader(ContextConstants.X_REQUEST_ID);
        if (StringUtil.isBlank(requestId)) {
            requestId = String.valueOf(IdWorker.getId());
        }
        ThreadContext.put(LogConstants.MDC_REQUEST_ID_KEY, requestId);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadContext.remove(LogConstants.MDC_REQUEST_ID_KEY);
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
