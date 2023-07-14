package com.rssl.phizic.scheduler;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.ModuleManager;
import com.rssl.phizic.module.loader.ModuleLoader;
import com.rssl.phizic.module.work.WorkManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;

/**
 * @author Erkin
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Слушатель джобов, выполняемых модулем
 * Задачи:
 * - настройка треда через использование WorkManager
 */
public class ModuleJobListener implements JobListener
{
	private final Log syslog = PhizICLogFactory.getLog(LogModule.Scheduler);

	private final Log consolelog = LogFactory.getLog(LogModule.Scheduler.name());

	/**
	 * Имя слушателя
	 * Задаётся в quartz.properties
	 */
	private String name;

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return name;
	}

	@SuppressWarnings("JavaDoc")
	public void setName(String name)
	{
		this.name = name;
	}

	public void jobToBeExecuted(JobExecutionContext context)
	{
		try
		{
			ModuleJob job = getModuleJob(context);
			if (job == null)
				return;
			ModuleManager moduleManager = getModuleManager(context);
			Module module = moduleManager.getModule(job.getModuleClass());
			ApplicationInfo.setCurrentApplication(module.getApplication());
			ModuleLoader moduleLoader = module.getModuleLoader();
			moduleLoader.start();
			job.setModule(module);

			WorkManager workManager = module.getWorkManager();
			workManager.beginWork();

			if (syslog.isDebugEnabled())
				syslog.debug("Готовится к запуску джоб " + context.getJobDetail().getName());
		}
		catch (RuntimeException e)
		{
			consolelog.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private ModuleJob getModuleJob(JobExecutionContext context)
	{
		Job result = context.getJobInstance();
		if (!(result instanceof ModuleJob))
			return null;
		return (ModuleJob) result;
	}

	private ModuleManager getModuleManager(JobExecutionContext context)
	{
		SchedulerContext schedulerContext = getSchedulerContext(context);
		ModuleManager result = (ModuleManager) schedulerContext.get(ModuleManager.class.getName());
		if (result == null)
			throw new RuntimeException("Отсутствует менеджер модулей");
		return result;
	}

	private SchedulerContext getSchedulerContext(JobExecutionContext jobContext)
	{
		try
		{
			return jobContext.getScheduler().getContext();
		}
		catch (SchedulerException e)
		{
			throw new RuntimeException("Ошибка получения конекста шедулера", e);
		}
	}

	public void jobExecutionVetoed(JobExecutionContext context)
	{
		try
		{
			ModuleJob job = getModuleJob(context);
			if (job == null)
				return;
			ModuleManager moduleManager = getModuleManager(context);
			Module module = moduleManager.getModule(job.getModuleClass());
			ApplicationInfo.setCurrentApplication(module.getApplication());

			if (syslog.isDebugEnabled())
				syslog.debug("Отклонён запуск джоба " + context.getJobDetail().getName());

			endWorkManager(context);
		}
		catch (RuntimeException e)
		{
			consolelog.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException)
	{
		try
		{
			ModuleJob job = getModuleJob(context);
			if (job == null)
				return;
			ModuleManager moduleManager = getModuleManager(context);
			Module module = moduleManager.getModule(job.getModuleClass());
			ApplicationInfo.setCurrentApplication(module.getApplication());

			String jobName = context.getJobDetail().getName();
			if (jobException == null)
			{
				if (syslog.isDebugEnabled())
					syslog.debug("Успешно завершил работу джоб " + jobName);
			}
			else
			{
				syslog.error("Аварийно завершил работу джоб " + jobName + " - " + jobException.getLocalizedMessage(), jobException);
			}

			endWorkManager(context);
		}
		catch (RuntimeException e)
		{
			consolelog.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private void endWorkManager(JobExecutionContext jobContext)
	{
		ModuleJob moduleJob = getModuleJob(jobContext);
		if (moduleJob != null)
		{
			WorkManager workManager = moduleJob.getModule().getWorkManager();
			workManager.endWork();
		}
	}
}
