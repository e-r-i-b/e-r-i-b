package com.rssl.phizic.test.way4u.context;

/**
 * Хибернейт-сервис для тестового приложения
 * @author Jatsky
 * @ created 26.01.15
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.dataaccess.hibernate.HibernateUtil;
import org.hibernate.HibernateException;

public class MockHibernateService extends HibernateService
{
	public void start() throws HibernateException
	{
		log.info("starting mockHibernateService: " + getJndiName());

		ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		try
		{
			setMapResources(null);
			readDatasourceConfig();
			loadHibernateCfg();
			addMappings();

			buildConfiguration().buildSessionFactory();

			String boundName = getJndiName();

			if (HibernateUtil.lookupSessionFactory(boundName) != null)
			{
				log.info("Hibernate factory loaded");
			}
			else
			{
				log.info("Hibernate factory not loaded");
			}

			startLogging();

			log.info("mockHibernateService started: " + getJndiName());
		}
		catch (HibernateException e)
		{
			log.fatal(e);
			throw e;
		}
		catch (Throwable t)
		{
			log.fatal(t);
			throw new HibernateException(t);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}
}

