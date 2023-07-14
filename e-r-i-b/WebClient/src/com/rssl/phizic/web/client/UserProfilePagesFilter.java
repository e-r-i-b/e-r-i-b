package com.rssl.phizic.web.client;

import java.io.IOException;
import javax.servlet.*;

/**
 * Фильтр проверки находимся мы в профиле клиента или нет.
 *
 * @author bogdanov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserProfilePagesFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		servletRequest.setAttribute("ifUserProfile", true);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{
	}
}
