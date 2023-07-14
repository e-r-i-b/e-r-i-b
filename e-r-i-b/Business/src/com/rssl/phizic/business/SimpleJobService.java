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
 * Базовый сервис для работы с джобами
 */
public class SimpleJobService
{
	public static final String QUARTZ_PROPERTIES = "quartz.properties";

	public static final String ERMB_QUARTZ_PROPERTIES = "ermb-quartz.properties";

	private static final Long MILSEK_HOUR = 3600000L; //миллисекунд в часе
	private static final String HOUR = "Hour";
	private static final String DAY = "Day";
	private static final String TIME = "Time";

	/**
	 * Мапа "имя ресурса с пропертями Кварца -> инстанс Кварца"
	 * Важно! Мапа должна использоваться только в конструкторе и только в блоке synchronized
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
	 * @param quartzPropertiesResourceName - имя ресурса с пропертями Кварца, например, "quartz.properties"
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
					throw new ConfigurationException("Сбой на создании инстанса кварца", e);
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
	 *  Добавить в текущий планировщик новый Cron джоб. Если джоб уже есть то он удаляется и
 	 *	в новый джоб в дополнительную информацию(jobDetail.JobDataMap) передается значение предыдущего запуска старого триггера пересоздаваемого джоба.
	 * @param jobName Имя джоба
	 * @param expression Cron ворожение
	 * @param jobCalss Класс джоба
	 * @param jobGroupName Имя группу
	 * @param map Доп. информация
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public void addNewCronJob(String jobName, String expression, String jobCalss, String jobGroupName,JobDataMap map)
			throws ParseException, QuartzException
	{
		try
		{
			//если дополнительные данные  не переданы то создаем новую мапу
			if (map == null || map.isEmpty())
				map = new JobDataMap();

			//получаем старый триггер джоба
			JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGroupName);

			CronTrigger cronTrigger = new CronTrigger(jobName, jobGroupName, jobName, jobGroupName, expression);
			//если джоб уже есть то удаляем его
			if (jobDetail!=null)
			{
				if (jobDetail.getJobDataMap().get("PreviousFireTime") != null)
				//передаем время предыдущего запуска старого триггера
				map.put("PreviousFireTime",jobDetail.getJobDataMap().get("PreviousFireTime"));

			    removeJob(jobName,jobGroupName);
				
			}
			else
			{
				//задаем триггеру идентификатор как у джоба
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
	 * Добавляем новый cron триггер к джобу, с заданным cron expression.
	 * Если джоб еще не создан, то он создается
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
	 * Добавляем переданнй триггер к джобу
	 * @param jobName
	 * @param jobClass
	 * @param jobGroupName
	 * @param trigger Новый триггер
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
	 * Добавляем новый simple триггер к джобу
	 * @param triggerName
	 * @param hourCount интервал повторения в часах
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
			/*интервал повторения*/
			long hourInMillis =  MILSEK_HOUR * hourCount;
			/*дата первого запуска*/
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
	 * Удалить триггер из планировщика
	 * @param triggerName имя триггера
	 * @param groupName  имя группы
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
	 * Удалить джоб и связанный с ним триггер из планировщика
	 * @param jobName имя job
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
	 * Возвращает  мапу со строками тайминга работы триггера 
	 * ключи мапаы.(hour|day|time)
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
	 * получить информации о запуске триггера:
	 *   SimpleTrigger: Часы
	 *   CronTrigger:  День, время
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
	 * Возвращает Cron выражение для переданного джоба
	 * @param jobName - имя джоба
	 * @param jobGroupName - имя группы джобов
	 * @return Cron выражение для переданного джоба, null если джоб не найден или триггер не является Cron 
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
	 * Получить конкретный триггер джоба
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName имя триггера который хотим получить
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
	 * Проверяем существует ли триггер
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
	 * Возвращает список триггеров джоба
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
	 * Обновить старый триггер новым
	 * @param oldTriggerName  старый триггер
	 * @param groupName
	 * @param newTrigger      новый триггер
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
	 * Обновляет для джоба интервал запуска триггера
	 * @param jobName - название джоба
	 * @param jobGroupName - название группы джоба
	 * @param triggerName - название триггера джоба
	 * @param triggerGroupName - название группы триггера джоба
	 * @param intervalInMillis - новый интервал повторениия в миллисек.
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
