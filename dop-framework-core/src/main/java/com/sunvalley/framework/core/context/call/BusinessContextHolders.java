package com.sunvalley.framework.core.context.call;

import com.sunvalley.framework.core.code.ApiCoreCode;
import com.sunvalley.framework.core.result.ResultAssert;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.function.Function;

/**
 * @Author: Simms.shi
 * @Date: 2019/7/29 20:16
 * @Desc: 业务请求上下文
 */
public class BusinessContextHolders {

    private static final ThreadLocal<BusinessContext> contextLocal = new ThreadLocal<>();


    public static BusinessContext getBusinessContext(boolean check) {
        BusinessContext context = contextLocal.get();
        ResultAssert.throwOnFalse(!check || context != null, ApiCoreCode.NO_CALL_CONTEXT);
        return context;
    }

    public static BusinessContext getBusinessContext() {
        return getBusinessContext(false);
    }

    /**
     * 为什么暴露给外界：让外界能在自启动线程中正确设定callcontext
     */
    public static void setContext(BusinessContext callContext) {
        if (callContext == null) {
            contextLocal.remove();
        } else {
            contextLocal.set(callContext);
        }
    }

    public static void removeContext() {
        contextLocal.remove();
    }

    @Nullable
    public static Long getXSystemId() {
        return getAttrByFunc(BusinessContext::getSystemId);
    }

    @Nullable
    public static Long getXRoleId() {
        return getAttrByFunc(BusinessContext::getRoleId);
    }

    @Nullable
    public static Long getXCompanyId() {
        return getAttrByFunc(BusinessContext::getCompanyId);
    }

    public static <T> T getAttrByFunc(Function<BusinessContext,T> supplier) {
        return Optional.ofNullable(getBusinessContext())
            .map(supplier)
            .orElse(null);
    }
}
