package com.sunvalley.framework.base.context.servlet;

import com.sunvalley.framework.core.context.ContextConstants;
import com.sunvalley.framework.core.context.call.BusinessContext;
import com.sunvalley.framework.core.context.call.BusinessContextHolders;
import com.sunvalley.framework.core.context.ext.ExtContextHolders;
import com.sunvalley.framework.core.context.plat.PlatContext;
import com.sunvalley.framework.core.context.plat.PlatContextHolders;
import com.sunvalley.framework.core.utils.Base64Util;
import com.sunvalley.framework.core.utils2.UtilJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 *
 */
@Slf4j
public class ControllerCallContextFilter extends HttpFilter {


	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			setPlatContext(request);
			setBusinessContext(request);
			chain.doFilter(request, response);
		} finally {
			PlatContextHolders.removePlatContext();
			BusinessContextHolders.removeContext();
			ExtContextHolders.clear();
		}
	}


	private void setPlatContext(HttpServletRequest request) {
		Optional.ofNullable(request.getHeader(ContextConstants.PLAT_CONTEXT_ID))
			.filter(s -> !StringUtils.isEmpty(s))
			.map(s -> UtilJson.readValue(Base64Util.decode(s), PlatContext.class))
			.ifPresent(PlatContextHolders::setContext);
	}

	private void setBusinessContext(HttpServletRequest request) {
		Optional.ofNullable(request.getHeader(ContextConstants.BUSINESS_CONTEXT_ID))
			.filter(s -> !StringUtils.isEmpty(s))
			.map(s -> UtilJson.readValue(Base64Util.decode(s), BusinessContext.class))
			.ifPresent(BusinessContextHolders::setContext);
	}


}
