package com.sunvalley.framework.base.logger.mdc;

import com.sunvalley.framework.core.context.ContextConstants;
import com.sunvalley.framework.core.context.LogConstants;
import com.sunvalley.framework.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring boot Mdc Log Filter
 *
 * @author L.cm
 */
@Slf4j
public class MdcLogFilter extends HttpFilter {

	/**
	 * Mdc Log Filter
	 *
	 * @param request  request
	 * @param response response
	 * @param chain    chain
	 * @throws IOException      Exception
	 * @throws ServletException Exception
	 */
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			String requestId = request.getHeader(ContextConstants.CALL_REQUEST_ID);
			if (StringUtils.isBlank(requestId)) {
				requestId = StringUtil.getUUID();
				ThreadContext.put(LogConstants.MDC_REQUEST_ID_KEY, requestId);
				log.info("xRequestId is null, requestURI:{}，newly requestId:{}",request.getRequestURI(), requestId);
			}else{
				ThreadContext.put(LogConstants.MDC_REQUEST_ID_KEY, requestId);
			}
			chain.doFilter(request, response);
		} finally {
			ThreadContext.remove(LogConstants.MDC_REQUEST_ID_KEY);
		}
	}
}
