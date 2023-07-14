package com.rssl.phizic.monitoring;

import org.quartz.ee.servlet.QuartzInitializerServlet;

import javax.servlet.ServletConfig;

/**
 * Сервлет шедулеров мониторинга
 * @author gladishev
 * @ created 12.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringSchedulerInitializerServlet extends QuartzInitializerServlet
{
	public void init(ServletConfig cfg) throws javax.servlet.ServletException
	{
		ClassLoader old = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
		try
		{
			super.init(cfg);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader( old );
		}
	}
}
