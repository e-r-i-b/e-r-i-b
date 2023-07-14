package com.rssl.phizic.utils.test;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.util.Map;

/**
 * Базовый класс для ant-их задач. Решает проблему с загрузчиками классов.
 *
 * @author Kidyaev
 * @ created 27.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 58429 $
 */
public abstract class SafeTaskBase extends Task
{
	private static final String PROPERTIES_PREFIX = "com.rssl";

	public void execute() throws BuildException
	{
		ApplicationInfo.setCurrentApplication(Application.PhizIA);
		ClassLoader classLoader = null;
		try
		{
			Project p = getProject();
			if(p != null)
			{
				Map<String, String> properties = p.getProperties();

				for (String key : properties.keySet())
				{
					if (key.startsWith(PROPERTIES_PREFIX))
					{
						System.setProperty(key, properties.get(key));
					}
				}
			}

			classLoader = Thread.currentThread().getContextClassLoader();

			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

			JUnitDatabaseConfig config =  getDatabaseConfig();

			if(config==null)
				RSSLTestCaseBase.initializeEnvironment();
			else
				RSSLTestCaseBase.initializeEnvironment(config);

			System.out.println("VVVVVVVVVVVVVVVVVVVVV "+this.getClass().getName() + " - выполняется... VVVVVVVVVVVVVVVVVVVVV");
			safeExecute();
			System.out.println("^^^^^^^^^^^^^^^^^^^^^ "+this.getClass().getName() + " - выполнено ^^^^^^^^^^^^^^^^^^^^^^^^^^");
		}
		catch(BuildException e)
		{
			throw e;
		}
		catch (Throwable e)
		{
			String stackTrace = "";
			StackTraceElement[] stackTraceElements = e.getStackTrace();
			for (StackTraceElement stackTraceElement : stackTraceElements)
				stackTrace += "\n         "+stackTraceElement.toString();
			System.out.println("error: " + e.getMessage() + " stack: "+ stackTrace);
			throw new BuildException(e);
		}
		finally
		{
			if (classLoader != null)
				Thread.currentThread().setContextClassLoader(classLoader);
			ApplicationInfo.setDefaultApplication();
		}
	}

	/**
	 * Получить конфиг с настройками БД
	 * @return
	 */
	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		return null;
	}

	public abstract void safeExecute() throws Exception;
}
