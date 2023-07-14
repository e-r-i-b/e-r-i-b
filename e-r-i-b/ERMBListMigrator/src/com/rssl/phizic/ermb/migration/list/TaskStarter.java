package com.rssl.phizic.ermb.migration.list;

import com.rssl.phizic.business.ermb.migration.list.ModuleExecutor;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.ModuleStaticManager;
import com.rssl.phizic.utils.StringHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Gulov
 * @ created 18.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������.
 * �������� �� ��������� �������� �������� ������ ������.
 * �������� ����� ������ �� ��� ��������.
 * ������� �������� �������.
 * ���������� ������ � ������� ������.
 */
public class TaskStarter implements ServletContextListener
{
	public static final String MODULE_CLASS_NAME = "moduleClassName";

	private ModuleExecutor executor;

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		ServletContext servletContext = servletContextEvent.getServletContext();
		String moduleClassName = getModuleClassName(servletContext);
		executor = new ModuleExecutor(ModuleStaticManager.getInstance(), getModuleClass(moduleClassName));
		executor.start();
	}

	private String getModuleClassName(ServletContext servletContext)
	{

		String result = servletContext.getInitParameter(MODULE_CLASS_NAME);
		if (StringHelper.isEmpty(result))
			throw new RuntimeException("�� ������� ��� ������ ������");
		return result;
	}

	private Class<? extends Module> getModuleClass(String moduleClassName)
	{
		try
		{
			Class<?> clazz = Class.forName(moduleClassName);
			if (!Module.class.isAssignableFrom(clazz))
				throw new RuntimeException("��������� ��� ������ ������� �� ��������");
			// noinspection unchecked
			return (Class<? extends Module>) clazz;
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("�� ������� ��� ������ ������");
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		executor.stop();
	}

}
