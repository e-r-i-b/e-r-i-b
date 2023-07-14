package com.rssl.phizic.listener;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author Evgrafov
 * @ created 29.06.2007
 * @ $Author: erkin $
 * @ $Revision: 69115 $
 */

public class LogContextListener implements JobListener
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	private String name;

	public LogContextListener() {
	}

	public String getName()
	{
		return name;
	}

	/**
	 * Установить имя лисенера
	 * @param name имя
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public void jobToBeExecuted(JobExecutionContext context)
	{
		initLogContext();
		LogFactory.getLog(LogModule.Core.name()).trace("Job to be executed : " + context.getJobDetail().getName());
	}

	public void jobExecutionVetoed(JobExecutionContext context)
	{
		LogFactory.getLog(LogModule.Core.name()).trace("Job vetoed : " + context.getJobDetail().getName());
		clearLogContext();
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException)
	{
		String jobName = context.getJobDetail().getName();
		if(jobException == null)
		{
			LogFactory.getLog(LogModule.Core.name()).trace("Job was executed : " + jobName);
		}
		else
		{
			LogFactory.getLog(LogModule.Core.name()).error("Job was failed : " + jobName, jobException);
		}
		clearLogContext();
	}

	private void initLogContext()
	{
	}

	private void clearLogContext()
	{
		LogThreadContext.clear();
	}
}