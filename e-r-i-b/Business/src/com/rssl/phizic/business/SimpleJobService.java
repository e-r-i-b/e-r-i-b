package com.rssl.phizic.business;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: Moshenko
 * Date: 23.06.2011
 * Time: 17:36:00
 * ������� ������ ��� ������ � �������
 */
public class SimpleJobService
{
	public static final String QUARTZ_PROPERTIES = "quartz.properties";

	public static final String ERMB_QUARTZ_PROPERTIES = "ermb-quartz.properties";

	private static final Long MILSEK_HOUR = 3600000L; //����������� � ����
	private static final String HOUR = "Hour";
	private static final String DAY = "Day";
	private static final String TIME = "Time";

	/**
	 * ���� "��� ������� � ���������� ������ -> ������� ������"
	 * �����! ���� ������ �������������� ������ � ������������ � ������ � ����� synchronized
	 */
	private static final Map<String, Scheduler> schedulerMap = new HashMap<String, Scheduler>();

	private final Scheduler scheduler;

	/**
	 * default ctor
	 */
	public SimpleJobService()
	{
		this(QUARTZ_PROPERTIES);
	}

	/**
	 * ctor
	 * @param quartzPropertiesResourceName - ��� ������� � ���������� ������, ��������, "quartz.properties"
	 */
	public SimpleJobService(String quartzPropertiesResourceName)
	{
		synchronized (schedulerMap)
		{
			Scheduler localScheduler = schedulerMap.get(quartzPropertiesResourceName);
			if (localScheduler == null)
			{
				try
				{
					Properties properties = new Properties();
					properties.load(ResourceHelper.loadResourceAsStream(quartzPropertiesResourceName));
					SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
					localScheduler = schedulerFactory.getScheduler();
					schedulerMap.put(quartzPropertiesResourceName, localScheduler);
				}
				catch (Exception e)
				{
					throw new ConfigurationException("���� �� �������� �������� ������", e);
				}
			}
			scheduler = localScheduler;
		}
	}

	private Class<Object> getJobClazz(String className)
	{
		try
		{
			return ClassHelper.loadClass(className);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 *  �������� � ������� ����������� ����� Cron ����. ���� ���� ��� ���� �� �� ��������� �
 	 *	� ����� ���� � �������������� ����������(jobDetail.JobDataMap) ���������� �������� ����������� ������� ������� �������� ���������������� �����.
	 * @param jobName ��� �����
	 * @param expression Cron ���������
	 * @param jobCalss ����� �����
	 * @param jobGroupName ��� ������
	 * @param map ���. ����������
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public void addNewCronJob(String jobName, String expression, String jobCalss, String jobGroupName,JobDataMap map)
			throws ParseException, QuartzException
	{
		try
		{
			//���� �������������� ������  �� �������� �� ������� ����� ����
			if (map == null || map.isEmpty())
				map = new JobDataMap();

			//�������� ������ ������� �����
			JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGroupName);

			CronTrigger cronTrigger = new CronTrigger(jobName, jobGroupName, jobName, jobGroupName, expression);
			//���� ���� ��� ���� �� ������� ���
			if (jobDetail!=null)
			{
				if (jobDetail.getJobDataMap().get("PreviousFireTime") != null)
				//�������� ����� ����������� ������� ������� ��������
				map.put("PreviousFireTime",jobDetail.getJobDataMap().get("PreviousFireTime"));

			    removeJob(jobName,jobGroupName);
				
			}
			else
			{
				//������ �������� ������������� ��� � �����
			    jobDetail = new JobDetail(jobName, jobGroupName, getJobClazz(jobCalss));
			}
			scheduler.addJob(jobDetail, true);
			cronTrigger.setJobDataMap(map);
			scheduler.scheduleJob(cronTrigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	 /**
	 * ��������� ����� cron ������� � �����, � �������� cron expression.
	 * ���� ���� ��� �� ������, �� �� ���������
	 * @param triggerName
	 * @param expression
	 * @param jobClass
	 * @param jobGroupName
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public void addNewCronTriggerToJob(String triggerName, String expression, String jobName,String jobClass, String jobGroupName)
		throws ParseException, QuartzException
	{
		try
		{
			CronTrigger cronTrigger = new CronTrigger(triggerName, jobGroupName, jobName, jobGroupName, expression);

			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, getJobClazz(jobClass));

			scheduler.addJob(jobDetail, true);

			scheduler.scheduleJob(cronTrigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ��������� ��������� ������� � �����
	 * @param jobName
	 * @param jobClass
	 * @param jobGroupName
	 * @param trigger ����� �������
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public void addNewTriggerToJob(String jobName,String jobClass, String jobGroupName,Trigger trigger)
		throws ParseException, QuartzException
	{
		try
		{
			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, getJobClazz(jobClass));
			scheduler.addJob(jobDetail, true);
			scheduler.scheduleJob(trigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ��������� ����� simple ������� � �����
	 * @param triggerName
	 * @param hourCount �������� ���������� � �����
	 * @param jobName
	 * @param jobClass
	 * @param jobGroupName
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public void addNewSimpleTriggerToJob(String triggerName, Long hourCount, String jobName,String jobClass, String jobGroupName)
		throws ParseException, QuartzException
	{
		try
		{
			/*�������� ����������*/
			long hourInMillis =  MILSEK_HOUR * hourCount;
			/*���� ������� �������*/
			Date triggerStartTime = new Date(System.currentTimeMillis() + hourInMillis);
			SimpleTrigger simpleTrigger = new SimpleTrigger(triggerName, jobGroupName, jobName, jobGroupName, triggerStartTime, null, -1, hourInMillis);

			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, getJobClazz(jobClass));

			scheduler.addJob(jobDetail, true);

			scheduler.scheduleJob(simpleTrigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ������� ������� �� ������������
	 * @param triggerName ��� ��������
	 * @param groupName  ��� ������
	 * @throws QuartzException
	 */
	public void deletTrigger(String triggerName, String groupName) throws QuartzException
	{
		try
		{
			scheduler.unscheduleJob(triggerName, groupName);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ������� ���� � ��������� � ��� ������� �� ������������
	 * @param jobName ��� job
	 * @throws QuartzException
	 */
	public void removeJob(String jobName, String jobGroupName) throws QuartzException
	{
		try
		{
			scheduler.deleteJob(jobName, jobGroupName);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ����������  ���� �� �������� �������� ������ �������� 
	 * ����� �����.(hour|day|time)
	 *
	 * @param jobName
	 * @return
	 * @throws QuartzException
	 */
	public Map<String, String> getTime(String jobName, String jobGroupName) throws QuartzException
	{
		Map<String, String> result = new HashMap<String, String>();
		try
		{
			Trigger trigger = scheduler.getTrigger(jobName, jobGroupName);
			getTimeFromTrigger(trigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}

		return result;
	}

	/**
	 * �������� ���������� � ������� ��������:
	 *   SimpleTrigger: ����
	 *   CronTrigger:  ����, �����
	 * @param trigger
	 * @return
	 */
	public Map<String, String> getTimeFromTrigger(Trigger trigger)
	{
		Map<String, String> result = new HashMap<String, String>();

		if (trigger != null)
			{
				if (trigger instanceof SimpleTrigger)
				{
					long repeatInterval = ((SimpleTrigger) trigger).getRepeatInterval();
					result.put(HOUR, DateHelper.toHour(repeatInterval));
				}
				else if (trigger instanceof CronTrigger)
				{
					Pair<String, String> time = DateHelper.getTimeFromExpression(((CronTrigger) trigger).getCronExpression().toString());
					result.put(DAY, time.getFirst());
					result.put(TIME, time.getSecond());
				}
			}
		return result;
	}

	/**
	 * ���������� Cron ��������� ��� ����������� �����
	 * @param jobName - ��� �����
	 * @param jobGroupName - ��� ������ ������
	 * @return Cron ��������� ��� ����������� �����, null ���� ���� �� ������ ��� ������� �� �������� Cron 
	 * @throws QuartzException
	 */
	public String getCronExpression(String jobName, String jobGroupName) throws QuartzException
	{
		try
		{
			Trigger trigger = scheduler.getTrigger(jobName, jobGroupName);
			if(trigger != null && trigger instanceof CronTrigger)
			{
				return ((CronTrigger) trigger).getCronExpression();
			}
			else
				return null;
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * �������� ���������� ������� �����
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName ��� �������� ������� ����� ��������
	 * @return
	 */
	public Trigger getTrigger(String jobName,String jobGroupName,String triggerName) throws QuartzException
	{
		Trigger[] triggers = getTriggers(jobName,jobGroupName);

		for(Trigger trigger: triggers)
		{
			if (trigger.getName().equals(triggerName))
				return trigger;
		}
		return null;
	}

	/**
	 * ��������� ���������� �� �������
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @return
	 * @throws QuartzException
	 */
	public boolean isTriggerExist(String jobName,String jobGroupName,String triggerName) throws QuartzException
	{
		Trigger trigger = getTrigger(jobName,jobGroupName,triggerName);
		if (trigger != null)
			return true;
		else return false;
	}

	/**
	 * ���������� ������ ��������� �����
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 */
	public Trigger[] getTriggers(String jobName,String jobGroupName) throws QuartzException
	{
		try
		{
			return scheduler.getTriggersOfJob(jobName,jobGroupName);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * �������� ������ ������� �����
	 * @param oldTriggerName  ������ �������
	 * @param groupName
	 * @param newTrigger      ����� �������
	 * @return
	 * @throws QuartzException
	 */
	public Date rescheduleJob(String oldTriggerName,String groupName, Trigger newTrigger) throws QuartzException
	{
		try
		{
			return scheduler.rescheduleJob(oldTriggerName,groupName,newTrigger);
		}
		catch (SchedulerException e)
		{
			throw new QuartzException(e);
		}
	}

	/**
	 * ��������� ��� ����� �������� ������� ��������
	 * @param jobName - �������� �����
	 * @param jobGroupName - �������� ������ �����
	 * @param triggerName - �������� �������� �����
	 * @param triggerGroupName - �������� ������ �������� �����
	 * @param intervalInMillis - ����� �������� ����������� � ��������.
	 * @throws QuartzException
	 * @throws ParseException
	 */
	public void rescheduleTriggerInterval(String jobName, String jobGroupName,
	                                             String triggerName, String triggerGroupName,
	                                             long intervalInMillis) throws QuartzException, ParseException
	{
		Trigger oldTrigger = getTrigger(jobName, jobGroupName, triggerName);
		if (oldTrigger == null)
			return;
		Trigger newTrigger;
		if (oldTrigger instanceof CronTrigger)
			newTrigger = new CronTrigger(triggerName, triggerGroupName, jobName, jobGroupName,
					((CronTrigger)oldTrigger).getCronExpression());
		else
		{
			Date triggerStartTime = new Date(System.currentTimeMillis() + intervalInMillis);
			newTrigger = new SimpleTrigger(triggerName, triggerGroupName, jobName, jobGroupName,
					triggerStartTime, null, -1, intervalInMillis);
		}
		try
		{
			scheduler.rescheduleJob(triggerName, triggerGroupName, newTrigger);
		}
		catch (SchedulerException qe)
		{
			throw new QuartzException(qe);
		}

	}
}
