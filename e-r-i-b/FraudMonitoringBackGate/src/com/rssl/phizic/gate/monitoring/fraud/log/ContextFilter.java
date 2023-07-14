package com.rssl.phizic.gate.monitoring.fraud.log;

import com.rssl.phizic.logging.LogThreadContext;

import java.io.IOException;
import javax.servlet.*;

/**
 * @author khudyakov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ContextFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		try
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			LogThreadContext.clear();
		}
	}

	public void destroy() {}
}
