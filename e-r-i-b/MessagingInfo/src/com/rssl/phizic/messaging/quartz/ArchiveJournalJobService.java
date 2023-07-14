package com.rssl.phizic.messaging.quartz;

import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Properties;

/**
 * @author eMakarov
 * @ created 13.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveJournalJobService
{
	public static final String JOB_KEY = "job";
	private static final Object SCHEDULER_LOCKER = new Object();
	private static volatile Scheduler scheduler = null;

	private static Scheduler getScheduler() throws QuartzException
	{
		Scheduler localScheduler = scheduler;
		if (localScheduler == null)
		{
			synchronized (SCHEDULER_LOCKER)
			{
				if (scheduler == null)
				{
					try
					{
						Properties properties = new Properties();
						properties.load(ResourceHelper.loadResourceAsStream("quartz.properties"));
						SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
						scheduler = schedulerFactory.getScheduler();
					}
					catch (Exception e)
					{
						throw new QuartzException(e);
					}
				}
				localScheduler = scheduler;
			}
		}
		return localScheduler;
	}

	private static String getJobGroupName()
	{
		return "DEFAULT";
	}

	public static String makeTriggerName(String jobName, String expression)
	{
		String triggerName = jobName;
		if (expression != null && expression.length() > 0)
		{
			triggerName += "[" + expression + "]";
		}
		return triggerName;
	}

	/**
	 * �������� ����� job.
	 * ��� job ������� ����������� ������, ������ ���� job � ������� ��� �� ���������.
	 * @param jobName ��� job :������ ��� distribution
	 * @param expression ��������� cron
	 * @param jobClassName ����� ��� ��������� ��������
	 * @throws java.text.ParseException
	 * @throws QuartzException
	 */
	public static void addNewJob(String jobName, String expression, String jobClassName)
			throws ParseException, QuartzException
	{
		try
		{
			String triggerName = makeTriggerName(jobName, expression);

			Class jobClass = getJobClass(jobClassName);
			JobDetail jobDetail = new JobDetail(triggerName, getJobGroupName(), jobClass);
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put(JOB_KEY, jobClassName);

			getScheduler().addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(triggerName/*triger name*/, getJobGroupName()/*triger group*/,
					triggerName/*job name*/, getJobGroupName()/*job group*/
					, expression);
			getScheduler().scheduleJob(cronTrigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	private static Class getJobClass(String jobClassName)
	{
		try
		{
			return ClassHelper.loadClass(jobClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException();
		}
	}

	/**
	 * �������� ���������� ��� job
	 * @param jobName ��� job   :������ ��� distribution.getKey()
	 * @param oldExpression  ���������� cron ���������
	 * @param newExpression  ����� cron ���������
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public static void updateJob(String jobName, String oldExpression, String newExpression)
			throws ParseException, QuartzException
	{
		try
		{
			String triggerName = makeTriggerName(jobName, oldExpression);
			Trigger trigger = getScheduler().getTrigger(triggerName, getJobGroupName());
			String oldJobName = trigger.getJobName();
			JobDetail jobDetail = getScheduler().getJobDetail(oldJobName, trigger.getGroup());
			addNewJob(jobName, newExpression, (String) jobDetail.getJobDataMap().get(JOB_KEY));
			removeJob(oldJobName);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ������� job � ��������� � ��� ������
	 * @param jobName ��� job � ������� distribution.getKey()
	 * @throws QuartzException
	 */
	public static void removeJob(String jobName, String expression) throws QuartzException
	{
		try
		{
			String trigerName = makeTriggerName(jobName, expression);
			getScheduler().deleteJob(trigerName, getJobGroupName());
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ������� job � ��������� � ��� ������
	 * @param jobName ��� job � ������� makeTriggerName(): distributionName + "[" + expression + "]"
	 * @throws QuartzException
	 */
	private static void removeJob(String jobName) throws QuartzException
	{
		try
		{
			getScheduler().deleteJob(jobName, getJobGroupName());
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ������� ��� ������������ job(�.�. �������� � ������ getJobGroupName() )
	 * @throws QuartzException
	 */
	public static void removeAllJobs() throws QuartzException
	{
		try
		{
			String[] jobNames = getScheduler().getJobNames(getJobGroupName());
			//������� ��� ������������
			for (String jobName : jobNames)
			{
				removeJob(jobName);
			}
		}
		catch (SchedulerException ex)
		{
			throw new QuartzException(ex);
		}
	}
}
