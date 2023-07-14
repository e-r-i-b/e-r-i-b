package com.rssl.phizic.web.common;

import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.io.IOException;
import javax.servlet.*;
/**
 * Фильтр для мобильного приложения
 * @author komarov
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class MobileLocaleContextFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		try
		{
			Store store = StoreManager.getCurrentStore();
			if(store != null)
				MultiLocaleContext.setLocaleId((String) store.restore(MultiLocaleContext.LOCALE_KEY));
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			MultiLocaleContext.setLocaleId(null);
		}

	}

	public void destroy()
	{

	}
}
