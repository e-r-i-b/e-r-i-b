package com.rssl.phizic.web;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Инициализатор контекста клиента.
 *
 * @author bogdanov
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ThreadLocalPersonDataInitializerListener implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		try
		{
			PersonDataProvider personDataProvider = new ThreadLocalPersonDataProvider();
			PersonContext.setPersonDataProvider(personDataProvider, Application.WSGateListener);
			PersonContext.setPersonDataProvider(personDataProvider, Application.WebShopListener);
			PersonContext.setPersonDataProvider(personDataProvider, Application.Scheduler);
			PersonContext.setPersonDataProvider(personDataProvider, Application.ESBERIBListener);
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
