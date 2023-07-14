package com.rssl.phizic.web.common;

import com.rssl.phizic.service.StartupServiceDictionary;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Erkin
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ModuleStartupServicesStarter implements ServletContextListener
{
	private static final String SERVICE_CONFIG_FILE_NAME = "module-startServices.xml";

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.setConfigFilePath(SERVICE_CONFIG_FILE_NAME);
		serviceStarter.startServices();
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.setConfigFilePath(SERVICE_CONFIG_FILE_NAME);
		serviceStarter.stopServices();
	}
}
