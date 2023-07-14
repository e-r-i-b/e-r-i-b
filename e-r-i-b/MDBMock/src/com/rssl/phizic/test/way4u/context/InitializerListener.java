package com.rssl.phizic.test.way4u.context;

/**
 * Инициализаторы для тестого приложения
 * @author Jatsky
 * @ created 26.01.15
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.LogFactory;

public class InitializerListener
{
	private final MockHibernateService phizICHibernateService = new MockHibernateService();
	private final MockHibernateService phizICLogHibernateService = new MockHibernateService();


	public void startTestServices()
	{
		try
		{
			XmlHelper.initXmlEnvironment();
		}
		catch (Exception ex)
		{
			LogFactory.getLog(LogModule.Core.name()).warn(ex);
		}

		phizICHibernateService.setJndiName("hibernate/session-factory/PhizIC");
		phizICHibernateService.setShowSqlEnabled("true");
		phizICHibernateService.setHibernateCfg("hibernate.cfg.xml");
		phizICHibernateService.start();

		phizICLogHibernateService.setJndiName("hibernate/session-factory/PhizICLog");
		phizICLogHibernateService.setShowSqlEnabled("true");
		phizICLogHibernateService.setHibernateCfg("hibernate.cfg.xml");
		phizICLogHibernateService.start();
	}

	public void destroyTestServices()
	{
		phizICHibernateService.stop();
		phizICLogHibernateService.stop();
		LogThreadContext.clear();
	}
}

