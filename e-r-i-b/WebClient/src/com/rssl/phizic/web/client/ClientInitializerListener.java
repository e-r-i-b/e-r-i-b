package com.rssl.phizic.web.client;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.web.common.client.HttpPersonDataProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Evgrafov
 * @ created 16.08.2007
 * @ $Author: nady $
 * @ $Revision: 74520 $
 */

public class ClientInitializerListener implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		try
		{
			PersonDataProvider personDataProvider = new HttpPersonDataProvider();
			PersonContext.setPersonDataProvider(personDataProvider, Application.PhizIC);
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
