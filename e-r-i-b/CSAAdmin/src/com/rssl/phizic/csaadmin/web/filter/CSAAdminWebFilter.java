package com.rssl.phizic.csaadmin.web.filter;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.IOException;
import javax.servlet.*;

/**
 * @author mihaylov
 * @ created 14.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр веб запросов в ЦСА админ
 */
public class CSAAdminWebFilter implements Filter
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB);
	private String requestEncoding =null;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		requestEncoding = filterConfig.getInitParameter("requestEncoding");
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		servletRequest.setCharacterEncoding(requestEncoding);
		try
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public void destroy()
	{

	}

}
