package com.rssl.phizic.web.common;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.logging.cache.LogCacheProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Omeliyanchuk
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Останавливает кеширование.
 */
public class CacheShootDownListener  implements ServletContextListener
{

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{

	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		CacheProvider.shutdown();
		LogCacheProvider.shutdown();
	}
}
