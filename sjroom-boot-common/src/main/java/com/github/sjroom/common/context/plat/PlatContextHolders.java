package com.github.sjroom.common.context.plat;

import com.github.sjroom.common.code.ApiCoreCode;
import com.github.sjroom.common.response.R;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;


/**
 * @Author: Simms.shi
 * @Date: 2019/7/29 20:16
 * @Desc: 平台请求上下文管理
 */
public class PlatContextHolders {

    private static final ThreadLocal<PlatContext> contextLocal = new ThreadLocal<>();


    public static PlatContext getPlatContext(boolean check) {
        PlatContext context = contextLocal.get();
        R.throwOnFalse(!check || context != null, ApiCoreCode.NO_PLAT_CONTEXT);
        return context;
    }

    public static PlatContext getPlatContext() {
        return getPlatContext(false);
    }

    /**
     * 为什么暴露给外界：让外界能在自启动线程中正确设定callcontext
     */
    public static void setContext(PlatContext platContext) {
        if (platContext == null) {
            contextLocal.remove();
        } else {
            contextLocal.set(platContext);
        }
    }

    @Nullable
    public static Long getXAccountId() {
        return getAttrByFunc(PlatContext::getAccountId);
    }


    public static <T> T getAttrByFunc(Function<PlatContext, T> supplier) {
        return Optional.ofNullable(getPlatContext())
                .map(supplier)
                .orElse(null);
    }

}
