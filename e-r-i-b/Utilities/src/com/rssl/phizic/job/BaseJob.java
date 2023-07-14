package com.rssl.phizic.job;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * базовый джоб для установки названия приложения в текущем потоке
 * @author basharin
 * @ created 12.03.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class BaseJob
{
	public final void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(getApplication());
		try
		{
			executeJob(context);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	protected Application getApplication()
	{
		return Application.Scheduler;
	}

	protected abstract void executeJob(JobExecutionContext context) throws JobExecutionException;
}
