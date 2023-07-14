package com.rssl.phizic.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Ћистенер запускающий и останавливающий сервисы
 */

public class StartupServiceDictionaryStarter implements ServletContextListener
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
