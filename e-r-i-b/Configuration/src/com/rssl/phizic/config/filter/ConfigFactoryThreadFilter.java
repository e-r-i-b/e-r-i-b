package com.rssl.phizic.config.filter;

import java.io.IOException;
import javax.servlet.*;

/**
 * фильтр для очистки текущих настроек в нити.
 *
 * @author bogdanov
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public class ConfigFactoryThreadFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{
	}
}
