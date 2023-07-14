package com.rssl.phizic.csaadmin.web.filter;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author akrenev
 * @ created 26.11.13
 * @ $Author$
 * @ $Revision$
 */

public class CSAAdminContextFilter implements Filter
{
	public void init(FilterConfig filterConfig){}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		initContext(servletRequest);
		try
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			clearContext();
		}
	}

	private void initContext(ServletRequest servletRequest)
	{
		String clientIP = ((HttpServletRequest) servletRequest).getHeader("BRIDGE-PEER-ADDRESS");
		LogThreadContext.setIPAddress(StringHelper.isNotEmpty(clientIP) ? clientIP : servletRequest.getRemoteAddr());
	}

	private void clearContext()
	{
		LogThreadContext.clear();
	}


	public void destroy(){}
}
