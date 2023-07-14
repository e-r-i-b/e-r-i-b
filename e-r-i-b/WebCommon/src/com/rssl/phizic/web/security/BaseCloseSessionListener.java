package com.rssl.phizic.web.security;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * базовый листенер для закрытия сессии. Нужен для установки текущего приложения
 * @author basharin
 * @ created 19.03.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class BaseCloseSessionListener implements HttpSessionListener, ServletContextListener
{
	private static final String APPLICATION_NAME = "applicationName";

	protected Application application;

	public final void sessionDestroyed(HttpSessionEvent httpSessionEvent)
	{
		boolean definedCurrentApplication = ApplicationInfo.isDefinedCurrentApplication();
		if (!definedCurrentApplication)
			ApplicationInfo.setCurrentApplication(application);

		try
		{
			doSessionDestroyed(httpSessionEvent);
		}
		catch (Exception ex)
		{
			LogFactory.getLog(LogModule.Core.name()).error(ex);
		}
		finally
		{
			if (!definedCurrentApplication)
				ApplicationInfo.setDefaultApplication();
		}

	}

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		ServletContext servletContext = servletContextEvent.getServletContext();

		String app = servletContext.getInitParameter(APPLICATION_NAME);

		if (StringHelper.isEmpty(app))
			throw new ConfigurationException("BaseCloseSessionListener не задан  параметр "+ APPLICATION_NAME);

		application = Application.valueOf(app);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
	}

	protected abstract void doSessionDestroyed(HttpSessionEvent httpSessionEvent);
}
