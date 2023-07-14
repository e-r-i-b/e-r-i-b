package com.rssl.phizic.business;

import com.rssl.phizic.listener.LogContextListener;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

/**
 * User: Moshenko
 * Date: 13.07.2011
 * Time: 11:56:18
 */
public class PausedJobContextListener extends LogContextListener
{
	public void jobToBeExecuted(JobExecutionContext context)
	{
		try
		{
			//���������������� ���������� ������
			context.getScheduler().standby();
		}
		catch (SchedulerException e)
		{
			log.debug("PausedJobContextListener �� ������� ���������� �����������",e);
		}
	    super.jobToBeExecuted(context);
	}
}
