package com.rssl.phizic.scheduler;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.module.Module;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author Gulov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ���� ������, ������������ ��������� ���������
 */
public abstract class ModuleJob implements Job
{
	/**
	 * ������, � ������� �������� ����
	 */
	private Module module;

	/**
	 * ���������� ����� ������
	 * @return ����� ������
	 */
	public abstract Class<? extends Module> getModuleClass();

	public void setModule(Module module)
	{
		this.module = module;
	}

	public Module getModule()
	{
		return module;
	}

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(getModule().getApplication());
		try
		{
			executeJob(context);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	protected abstract void executeJob(JobExecutionContext context) throws JobExecutionException;
}
