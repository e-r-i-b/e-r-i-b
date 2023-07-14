package com.rssl.phizic.web.common.mobile;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.HttpPersonDataProvider;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Evgrafov
 * @ created 16.08.2007
 * @ $Author: emakarov $
 * @ $Revision: 8720 $
 */

public class ClientInitializerListener implements ServletContextListener
{
	private static final String AUTHENTICATION_MODES_CONFIG = "authenticationModesConfig";
	public static final String APPLICATION_NAME = "applicationName";

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		ServletContext servletContext = servletContextEvent.getServletContext();

		String app = servletContext.getInitParameter(APPLICATION_NAME);

		if (StringHelper.isEmpty(app))
			throw new ConfigurationException("ClientInitializerListener не задан  параметр " + APPLICATION_NAME);

		Application application = Application.valueOf(app);

		String authenticationModesConfig = servletContext.getInitParameter(AUTHENTICATION_MODES_CONFIG);
		if (StringHelper.isEmpty(authenticationModesConfig))
			throw new ConfigurationException("Не указан конфигурационный файл с описанием шагов аутентификации. " +
					"См. web.xml параметр " + AUTHENTICATION_MODES_CONFIG);

		try
		{
			PersonDataProvider personDataProvider = new HttpPersonDataProvider();
			PersonContext.setPersonDataProvider(personDataProvider, application);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
	}
}