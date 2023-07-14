package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.Constants;
import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.dataaccess.config.CSADataSourceConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.naming.NameNotFoundException;

/**
 *
 * Базовый класс для создания ant-tasks работающих со схемой CSA.
 *
 * @author  Balovtsev
 * @version 04.04.13 15:11
 */
public abstract class CSASafeTaskBase extends SafeTaskBase
{
	private final static String DEFAULT_CSA_HIBERNATE_JNDI        = "hibernate/session-factory/PhizICCSA";
	private final static String DEFAULT_CSA_DATASOURCE_CONF_CLASS = "com.rssl.phizic.dataaccess.config.CSADataSourceConfig";

	protected HibernateService getHibernateService()
	{
		HibernateService hibernateService = new HibernateService();
		hibernateService.setJndiName(DEFAULT_CSA_HIBERNATE_JNDI);
		hibernateService.setShowSqlEnabled(String.valueOf(true));
		hibernateService.setDatasourceConfigClass(DEFAULT_CSA_DATASOURCE_CONF_CLASS);
		return hibernateService;
	}

	protected String[] getConfigs()
	{
		return new String[] {"com/rssl/phizic/business/ext/sbrf/csa/csa-business.cfg.xml"};
	}

	protected Session getSession() throws HibernateException
	{
		try
		{
			startCSAHibernateService();
			return HibernateExecutor.getSessionFactory(Constants.DB_CSA).openSession();
		}
		catch (NameNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		return new CSAJUnitDatabaseConfig(new CSADataSourceConfig());
	}

	private void startCSAHibernateService() throws HibernateException
	{
		HibernateService hibernateService = getHibernateService();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		for (String config : getConfigs())
		{
			if (classLoader.getResourceAsStream(config) != null)
			{
				hibernateService.addConfigResource(config);
			}
		}

		hibernateService.start();
	}
}
