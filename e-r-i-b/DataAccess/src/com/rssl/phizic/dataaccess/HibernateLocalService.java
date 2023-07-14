package com.rssl.phizic.dataaccess;

import com.rssl.phizic.context.ModuleContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngine;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * @author Evgrafov
 * @ created 27.06.2006
 * @ $Author: erkin $
 * @ $Revision: 35497 $
 */

/**
 * Загрузка+выгрузка хибернейт-фабрик с локальной регистрацией (в HibernateContext)
 */
public class HibernateLocalService extends HibernateStartupServiceBase
{
	private String instanceName;

	/////////////////////////////////////////////////////////////////////////////////////

	public String getInstanceName()
	{
		return instanceName;
	}

	public void setInstanceName(String instanceName)
	{
		if (StringHelper.isEmpty(instanceName))
			throw new IllegalArgumentException("Аргумент 'instanceName' не может быть пустым");

		this.instanceName = instanceName;
	}

	public boolean isInitialized()
	{
		if (getInstanceName() == null)
			throw new IllegalStateException("Не указан параметр 'instanceName'");

		HibernateEngine hibernateEngine = getHibernateEngine();
		return hibernateEngine.getSessionFactory(getInstanceName()) != null;
	}

	public void start() throws HibernateException
	{
		if (getInstanceName() == null)
			throw new IllegalStateException("Не указан параметр 'instanceName'");

		HibernateEngine hibernateEngine = getHibernateEngine();
		
		log.info("Загрузка хибернейт фабрики: " + getInstanceName());
		try
		{
			setMapResources(null);
			readDatasourceConfig();
			loadHibernateCfg();
			addMappings();

			SessionFactory factory = buildConfiguration().buildSessionFactory();
			hibernateEngine.addSessionFactory(getInstanceName(), factory);
			log.info("Хибернейт фабрика загружена");

			startLogging();
		}
		catch (HibernateException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new HibernateException(e);
		}
	}

	public void stop()
	{
		if (getInstanceName() == null)
			throw new IllegalStateException("Не указан параметр 'instanceName'");

		log.info("Выгрузка хибернейт фабрики: " + getInstanceName());

		HibernateEngine hibernateEngine = getHibernateEngine();
		
		try
		{
			stopLogging();
			SessionFactory factory = hibernateEngine.getSessionFactory(getInstanceName());
			factory.close();
			hibernateEngine.removeSessionFactory(getInstanceName());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private HibernateEngine getHibernateEngine()
	{
		Module module = ModuleContext.getModule();
		if (module == null)
			throw new IllegalStateException("Не известен модуль");

		HibernateEngine hibernateEngine = module.getHibernateEngine();
		if (hibernateEngine == null)
			throw new UnsupportedOperationException("Хибернейт не поддерживается в модуле " + module.getName());

		return hibernateEngine;
	}
}
