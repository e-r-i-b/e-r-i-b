package com.rssl.phizic.config;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import javax.servlet.*;

/**
 * установка названия приложения в текущем потоке
 * @author basharin
 * @ created 04.03.14
 * @ $Author$
 * @ $Revision$
 */

public class ApplicationInfoFilter implements Filter
{
	private Application application;

	public void init(FilterConfig config) throws ServletException
	{
		String app = config.getInitParameter("application");

		if (StringHelper.isEmpty(app))
			throw new ServletException("ApplicationInfoFilter не задан  параметр application ");

		application = Application.valueOf(app);
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
	{
		try
		{
			ApplicationInfo.setCurrentApplication(application);
			chain.doFilter(req, resp);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	public void destroy()
	{
	}
}
