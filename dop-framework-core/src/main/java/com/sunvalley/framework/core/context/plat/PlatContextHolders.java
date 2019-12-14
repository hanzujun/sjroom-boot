package com.sunvalley.framework.core.context.plat;

import com.sunvalley.framework.core.code.ApiCoreCode;
import com.sunvalley.framework.core.code.LanguageEnum;
import com.sunvalley.framework.core.result.ResultAssert;
import org.springframework.lang.Nullable;

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
		ResultAssert.throwOnFalse(!check || context != null, ApiCoreCode.NO_PLAT_CONTEXT);
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


	public static void removePlatContext() {
		contextLocal.remove();
	}

	@Nullable
	public static String getXAuthToken() {
		return getAttrByFunc(PlatContext::getAuthToken);
	}

	@Nullable
	public static Long getXAccountId() {
		return getAttrByFunc(PlatContext::getAccountId);
	}

	@Nullable
	public static LanguageEnum getXLanguage() {
		return getAttrByFunc(PlatContext::getLanguage);
	}

	@Nullable
	public static Long getXTenantId() {
		return getAttrByFunc(PlatContext::getTenantId);
	}

//    @Nullable
//    public static String getXRequestId() {
//        return getAttrByFunc(PlatContext::getRequestId);
//    }

	public static <T> T getAttrByFunc(Function<PlatContext, T> supplier) {
		return Optional.ofNullable(getPlatContext())
			.map(supplier)
			.orElse(null);
	}

}
