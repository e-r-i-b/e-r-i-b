package com.rssl.phizic.web.security;

import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.io.IOException;
import javax.servlet.*;

/**
 * @author Gainanov
 * @ created 19.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фильтр сбрасывает сессионные данные пользователя
 * Используется для страниц входа в систему
 */
public class LoginUrlFilter implements Filter
{
	public void init(FilterConfig config) throws ServletException
	{
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
	{
		if (SecurityUtil.isAuthenticationComplete())
		{
			Store store = StoreManager.getCurrentStore();
			store.clear();
		}

		chain.doFilter(req, resp);
	}

	public void destroy()
	{
	}
}
