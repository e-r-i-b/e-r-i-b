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
 * ��������+�������� ���������-������ � ��������� ������������ (� HibernateContext)
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
			throw new IllegalArgumentException("�������� 'instanceName' �� ����� ���� ������");

		this.instanceName = instanceName;
	}

	public boolean isInitialized()
	{
		if (getInstanceName() == null)
			throw new IllegalStateException("�� ������ �������� 'instanceName'");

		HibernateEngine hibernateEngine = getHibernateEngine();
		return hibernateEngine.getSessionFactory(getInstanceName()) != null;
	}

	public void start() throws HibernateException
	{
		if (getInstanceName() == null)
			throw new IllegalStateException("�� ������ �������� 'instanceName'");

		HibernateEngine hibernateEngine = getHibernateEngine();
		
		log.info("�������� ��������� �������: " + getInstanceName());
		try
		{
			setMapResources(null);
			readDatasourceConfig();
			loadHibernateCfg();
			addMappings();

			SessionFactory factory = buildConfiguration().buildSessionFactory();
			hibernateEngine.addSessionFactory(getInstanceName(), factory);
			log.info("��������� ������� ���������");

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
			throw new IllegalStateException("�� ������ �������� 'instanceName'");

		log.info("�������� ��������� �������: " + getInstanceName());

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
			throw new IllegalStateException("�� �������� ������");

		HibernateEngine hibernateEngine = module.getHibernateEngine();
		if (hibernateEngine == null)
			throw new UnsupportedOperationException("��������� �� �������������� � ������ " + module.getName());

		return hibernateEngine;
	}
}
