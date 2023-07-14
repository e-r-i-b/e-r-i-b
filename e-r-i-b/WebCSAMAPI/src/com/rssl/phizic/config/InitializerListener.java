package com.rssl.phizic.config;

import com.rssl.phizic.service.StartupServiceDictionary;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 */
public class InitializerListener implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startServices();
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.stopServices();
		serviceStarter.stopMBeans();
	}
}
