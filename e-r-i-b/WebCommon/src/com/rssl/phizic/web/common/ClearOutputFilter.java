package com.rssl.phizic.web.common;

import com.rssl.phizic.web.servlet.HttpServletResponseJSPFilter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Rydvanskiy
 * @ created 12.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClearOutputFilter implements Filter
{
	public void destroy()
	{
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
	{
		if ( !(req instanceof HttpServletRequest && resp instanceof HttpServletResponse) )
		{
			chain.doFilter(req, resp);
			return ;
		}

		// Подменяем Response
		HttpServletResponseJSPFilter clearResponse = new HttpServletResponseJSPFilter((HttpServletResponse) resp);

		try
		{
			chain.doFilter(req, clearResponse);
		}
		finally
		{
			clearResponse.flushBuffer();
		}

	}

	public void init(FilterConfig config) throws ServletException
	{

	}
}
