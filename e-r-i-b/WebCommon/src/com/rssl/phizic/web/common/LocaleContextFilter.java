package com.rssl.phizic.web.common;

import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

import java.io.IOException;
import javax.servlet.*;

/**
 * @author mihaylov
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр для установки в контекст идентификатора текущей локали пользователя.
 */
public class LocaleContextFilter implements Filter
{
	private static final String APPLICATION = "applicationName";
	private Application application;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		application = Application.valueOf(filterConfig.getServletContext().getInitParameter(APPLICATION));
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		try
		{
			if(PersonContext.isAvailable())
			{
				String localeId = ConfigFactory.getConfig(UserPropertiesConfig.class).getERIBLocaleId(application);
				MultiLocaleContext.setLocaleId(localeId);
			}
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
