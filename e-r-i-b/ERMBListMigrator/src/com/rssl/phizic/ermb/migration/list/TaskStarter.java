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
 * Слушатель.
 * Получает из контекста сервлета название класса модуля.
 * Получает класс модуля по его названию.
 * Создает менеджер модулей.
 * Инициирует запуск и останов модуля.
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
			throw new RuntimeException("Не найдено имя класса модуля");
		return result;
	}

	private Class<? extends Module> getModuleClass(String moduleClassName)
	{
		try
		{
			Class<?> clazz = Class.forName(moduleClassName);
			if (!Module.class.isAssignableFrom(clazz))
				throw new RuntimeException("Указанное имя класса модулем не является");
			// noinspection unchecked
			return (Class<? extends Module>) clazz;
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Не найдено имя класса модуля");
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		executor.stop();
	}

}
