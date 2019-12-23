package github.sjroom.common.web;

import github.sjroom.common.context.ContextConstants;
import github.sjroom.common.logger.LogInitializer;
import github.sjroom.common.util.JsonUtil;
import github.sjroom.common.util.ObjectUtil;
import github.sjroom.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Spring boot 控制器 请求日志，方便代码调试
 * <p>
 * 根据自定义logger进行输出，level必须是debug以下才进行输出
 * * 使用方式:
 * * 例如 logging.level.mvc.api=debug 或者 logging.level.mvc.api.xxx.xxx.xxx=debug ,才将制定的请求进行log输出
 * 1  优化性能
 * 2，日志中可能包含敏感信息，在高级别环境中，默认是关闭的，在调试或者特殊需求，可以指定打开
 *
 * @author L.cm
 */
@Slf4j
@Aspect
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AccessWebAspect {

    /**
     * AOP 环切 控制器 R 返回值
     * <p>
     * * @see GetMapping
     * * @see PostMapping
     * * @see PutMapping
     * * @see DeleteMapping
     * * @see PatchMapping
     *
     * @param point JoinPoint
     * @return Object
     * @throws Throwable 异常
     */
    @Around(
            "execution(public !static * *(..)) && "
                    + "(@within(org.springframework.stereotype.Controller)"
                    + " || @within(org.springframework.web.bind.annotation.RestController) )"
                    + " && (@annotation(org.springframework.web.bind.annotation.RequestMapping)"
                    + " || @annotation(org.springframework.web.bind.annotation.GetMapping) "
                    + " || @annotation(org.springframework.web.bind.annotation.PostMapping) "
                    + " || @annotation(org.springframework.web.bind.annotation.PutMapping) "
                    + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
                    + " || @annotation(org.springframework.web.bind.annotation.PatchMapping))"
    )
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {

        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        HttpServletRequest request = WebUtil.getRequest();
        String requestURI = request != null ? request.getRequestURI() : null;
        String requestMethod = request != null ? request.getMethod() : null;
        long startNs = System.nanoTime();


        // 打印执行时间
        printRequestLog(request, requestURI, requestMethod);
        Class<?> returnType = method.getReturnType();
        Object result = null;
        refreshLogConfig(request);
        if (void.class == returnType) {
            point.proceed();
        } else {
            result = point.proceed();
        }
        refreshLogConfig(request);
        printResponseLog(requestURI, requestMethod, startNs, returnType, result);
        return result;
    }

    private void refreshLogConfig(HttpServletRequest request) {
        String xDebug = request.getHeader(ContextConstants.X_DEBUG);
        if (ObjectUtil.isNotEmpty(xDebug)) {
            LogLevel logLevel = null;
            try {
                logLevel = LogLevel.valueOf(request.getHeader(ContextConstants.X_DEBUG));
            } catch (Exception ex) {
                log.warn("logLevel is empty ex:{}", ex);
            }
            if (ObjectUtil.isNotNull(logLevel)) {
                LogInitializer.refreshLogConfig(xDebug, this.getClass());
            }
        }
    }

    private void printRequestLog(HttpServletRequest request, String requestURI, String requestMethod) {
        try {

//            Object[] args = point.getArgs();
            // 构建成一条长 日志，避免并发下日志错乱
            StringBuilder beforeReqLog = new StringBuilder(300);
            // 日志参数
            List<Object> beforeReqArgs = new ArrayList<>();
            beforeReqLog.append("\n\n================  Request Start  ================\n");
            // 打印路由
            beforeReqLog.append("===> {}: {}\n");
            beforeReqArgs.add(requestMethod);
            beforeReqArgs.add(requestURI);

            // 只打印透传的 header，打印时 Base64 解密
            beforeReqLog.append("================   Request End   ================\n");
            log.debug(beforeReqLog.toString(), beforeReqArgs.toArray());
        } catch (Exception e) {
            log.warn("printRequestLog failed;ex:{},msg:{}", e.getClass(), e.getMessage());
        }

    }

    private void printResponseLog(String requestURI, String requestMethod, long startNs, Class<?> returnType, Object result) {
        try {
            // aop 执行后的日志
            StringBuilder afterReqLog = new StringBuilder(200);
            afterReqLog.append("\n\n================  Response Start  ================\n");
            // 日志参数
            List<Object> afterReqArgs = new ArrayList<>();
            // 非 null 打印返回结构体
            if (result != null && JsonUtil.getInstance().canSerialize(returnType)) {
                afterReqLog.append("===Result===  {}\n");
                afterReqArgs.add(JsonUtil.toJson(result));
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            afterReqLog.append("<=== {}: {} ({} ms)\n");
            afterReqArgs.add(requestMethod);
            afterReqArgs.add(requestURI);
            afterReqArgs.add(tookMs);
            afterReqLog.append("================   Response End   ================\n");

            log.debug(afterReqLog.toString(), afterReqArgs.toArray());
        } catch (Exception e) {
            log.warn("printResponseLog failed;ex:{},msg:{}", e.getClass(), e.getMessage());
        }
    }
}
