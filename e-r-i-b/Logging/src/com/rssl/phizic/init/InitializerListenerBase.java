package com.rssl.phizic.init;

import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Базовый класс для инициализаторов.
 *
 * @author bogdanov
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 */

public class InitializerListenerBase implements ServletContextListener
{
	public final void contextInitialized(ServletContextEvent servletContextEvent)
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startServices();
		serviceStarter.startMBeans();

		try
		{
			XmlHelper.initXmlEnvironment();
		}
		catch (Exception ex)
		{
			LogFactory.getLog(LogModule.Core.name()).warn(ex);
		}
		onInitialized(servletContextEvent);
	}

	public final void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		onDestroyed(servletContextEvent);
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.stopServices();
		serviceStarter.stopMBeans();
		LogThreadContext.clear();
	}

	/**
	 * при инициализации.
	 *
	 * @param servletContextEvent
	 */
	protected void onInitialized(ServletContextEvent servletContextEvent){}

	/**
	 * при завершении.
	 *
	 * @param servletContextEvent
	 */
	protected void onDestroyed(ServletContextEvent servletContextEvent){}
}
