package com.rssl.phizic.config;

import com.rssl.common.forms.types.SimpleTypesConfig;
import com.rssl.common.forms.types.TypesConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogConfig;
import com.rssl.phizic.logging.messaging.MessageLogConfigImpl;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.SystemLogConfig;
import com.rssl.phizic.logging.system.SystemLogConfigImpl;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.web.common.ContextFilter;

import java.io.InvalidClassException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author osminin
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public class InitializerListener implements ServletContextListener
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		ServletContext servletContext = servletContextEvent.getServletContext();

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
