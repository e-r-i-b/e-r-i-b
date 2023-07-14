package com.rssl.phizic.test;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.init.InitializerListenerBase;
import com.rssl.phizic.config.PropertyReader;

import javax.servlet.ServletContextEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Egorovaa
 * Date: 09.11.2011
 * Time: 12:45:43
 */
public class InitializerListener extends InitializerListenerBase
{
	private final MockHibernateService hibernateService = new MockHibernateService();
	private final MockHibernateService ermbHibernateService = new MockHibernateService();
	private final MockHibernateService mbvHibernateService = new MockHibernateService();
	private final MockHibernateService mfmHibernateService = new MockHibernateService();

	@Override
	public void onInitialized(ServletContextEvent servletContextEvent)
	{
		PropertyReader propertyReader = ConfigFactory.getReaderByFileName("esberib.mock.properties");
		if (Boolean.parseBoolean(propertyReader.getProperty("esberib.mock.db.used")))
		{
			hibernateService.setJndiName("hibernate/session-factory/ESBERIBMock");
			hibernateService.setShowSqlEnabled("true");
			hibernateService.setHibernateCfg("ESBERIBMock.hibernate.cfg.xml");
			hibernateService.start();
		}
		ermbHibernateService.setJndiName("hibernate/session-factory/ErmbMock");
		ermbHibernateService.setShowSqlEnabled("true");
		ermbHibernateService.setHibernateCfg("ErmbMock.hibernate.cfg.xml");
		ermbHibernateService.start();

		mbvHibernateService.setJndiName("hibernate/session-factory/MBVMock");
		mbvHibernateService.setShowSqlEnabled("true");
		mbvHibernateService.setHibernateCfg("MBVMock.hibernate.cfg.xml");
		mbvHibernateService.start();

		mfmHibernateService.setJndiName("hibernate/session-factory/MFMMock");
		mfmHibernateService.setShowSqlEnabled("true");
		mfmHibernateService.setHibernateCfg("MFMMock.hibernate.cfg.xml");
		mfmHibernateService.start();
	}

	@Override
	public void onDestroyed(ServletContextEvent servletContextEvent)
	{
		hibernateService.stop();
		ermbHibernateService.stop();
		mbvHibernateService.stop();
	}
}
