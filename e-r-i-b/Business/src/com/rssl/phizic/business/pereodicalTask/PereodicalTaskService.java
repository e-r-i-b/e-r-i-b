package com.rssl.phizic.business.pereodicalTask;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.background.TaskResult;
import com.rssl.phizic.business.operations.background.TaskState;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: Moshenko
 * Date: 01.11.2011
 * Time: 18:07:18
 * Сервис для работы с периодическими  задачами, и всем что с ними связанно.
 */
public class PereodicalTaskService
{
	private static final SimpleService simpleService = new SimpleService();
    private static final CounterService counterService = new CounterService();

	private static final String TRIGGER_PREFIX = "PEREODICAL_";
	private static final String UNLOAD_JOB_NAME = "PereodicalJob"; // класс джоба переодических задач.
	private static final String UNLOAD_JOB_CLASS_NAME = "com.rssl.phizic.unloading.PereodicalJob"; // класс джоба переодических задач.
	private static final String UNLOAD_JOB_GROUP = "PereodicalGroup"; // имя группы.
	private static final Long MILSEK_HOUR = 3600000L;

	private static final String NO_TIME_EX = "Не заданно ни cronExp, ни timeInterval";

	private final SimpleJobService jobService = new SimpleJobService();

	/**
	 * Обновить фоновую задачу
	 * @param task задача для обновления
	 * @throws BusinessException
	 */
	public void addOrUpdate(PereodicalTask task) throws BusinessException
	{
		simpleService.addOrUpdate(task);
	}

	/**
	 * Добавляем новое периодическоое заднаие, генерируем уникальное имя триггера
	 * @param task
	 * @throws BusinessException
	 */
	@Transactional
	public PereodicalTask addTask(PereodicalTask task) throws BusinessException
	{
		task.setTriggerName(getTriggerUniqueName());
		task.setState(TaskState.PERIODICAL);
		task.setCreationDate(Calendar.getInstance());
		//Если присутствует крон выражение то добавляем cronTrigger, в противном случаи simpleTrigger,
		String cronExp = task.getCronExp();
		Long timeInterval = task.getTimeInterval();
		try
		{
			 if (!StringHelper.isEmpty(cronExp))
			 {
				jobService.addNewCronTriggerToJob(task.getTriggerName(),cronExp,UNLOAD_JOB_NAME,UNLOAD_JOB_CLASS_NAME,UNLOAD_JOB_GROUP);
			 }
			 else if (timeInterval!=null)
			 {
				 jobService.addNewSimpleTriggerToJob(task.getTriggerName(),timeInterval,UNLOAD_JOB_NAME,UNLOAD_JOB_CLASS_NAME,UNLOAD_JOB_GROUP);
			 }
			 else
				throw new BusinessException(NO_TIME_EX);
		}
		catch(ParseException pe)
		{
			throw new BusinessException(pe);
		}
		catch (QuartzException qe)
		{
			throw new BusinessException(qe);
		}
		return simpleService.add(task);
	}

	/**
	 * Обновляем таск, в зависимости от того был ли передан timeInterval или  cronExp
     * к нему будет прикреплен либо SimpleTrigger либо CronTrigger, старый триггер(по имени в таске) будет удален
	 * Примечание: у таска уже должен быть триггер, иначе новый триггер не добавится 
	 * @param task
	 * @throws BusinessException
	 */
	public void updateTask(PereodicalTask task) throws BusinessException
	{

		String cronExp = task.getCronExp();
		Long timeInterval = task.getTimeInterval();
		String triggerName = task.getTriggerName();
		try
		{
			Trigger trigger;
    		if (!StringHelper.isEmpty(cronExp))
			 {
				trigger = new CronTrigger(triggerName,UNLOAD_JOB_GROUP,UNLOAD_JOB_NAME,UNLOAD_JOB_GROUP,cronExp);
			 }
			 else if (timeInterval!=null)
			 {
			   /*интервал повторения*/
				long hourInMillis =  MILSEK_HOUR * timeInterval;
				/*дата первого запуска*/
				Date triggerStartTime = new Date(System.currentTimeMillis() + hourInMillis); 
			    trigger = new SimpleTrigger(triggerName, UNLOAD_JOB_GROUP, UNLOAD_JOB_NAME, UNLOAD_JOB_GROUP, new Date(), null, -1, 10000l);
			 }
			 else
				throw new BusinessException(NO_TIME_EX);

			if (jobService.isTriggerExist(UNLOAD_JOB_NAME,UNLOAD_JOB_GROUP,triggerName))
				/*обновляем триггер*/
				jobService.rescheduleJob(task.getTriggerName(),UNLOAD_JOB_GROUP,trigger);
			else
			{   /*или добавляем новый*/
				jobService.addNewTriggerToJob(UNLOAD_JOB_NAME,UNLOAD_JOB_CLASS_NAME,UNLOAD_JOB_GROUP,trigger);
			}

		}
		catch(ParseException pe)
		{
			throw new BusinessException(pe);
		}
		catch (QuartzException qe)
		{
			throw new BusinessException(qe);
		}
		 /*обновляем таск*/
		simpleService.addOrUpdate(task);
	}

	/**
	 *  Приостанавливает работу задания
	 * (удаляется триггера, состояние DISABLED)
	 * @param task задание для приостановки
	 * @throws BusinessException
	 */
	public void stopTask(PereodicalTask task) throws BusinessException
	{
		task.setState(TaskState.DISABLED);
		try
		{
			jobService.deletTrigger(task.getTriggerName(),UNLOAD_JOB_GROUP);
		}
		catch (QuartzException e)
		{
			throw new BusinessException(e);
		}
		simpleService.addOrUpdate(task);
	}

	/**
	 * Получить уникальное имя триггера
	 * @return
	 */
	private String getTriggerUniqueName() throws BusinessException
	{
		String countSting = "";
		try
		{
			countSting = counterService.getNext(Counters.PEREODICAL_QRTZ_TRIGGER_ENTRY).toString();
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}

		return TRIGGER_PREFIX + countSting;
	}

	/**
	 * Получить таск по имени триггера
	 * @param triggerName  имя триггера
	 * @return
	 * @throws BusinessException
	 */
	public PereodicalTask getTaskByTriggerName(final String triggerName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PereodicalTask>()
			{
				public PereodicalTask run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pereodicalTask.PereodicalTask.getTaskByTriggerName");
					query.setParameter("triggerName",triggerName);
					return (PereodicalTask)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить таск по имени триггера.
	 * @param triggerName имя триггера
	 * @throws BusinessException
	 */
	public void removeTaskByTriggerName(final String triggerName) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pereodicalTask.PereodicalTask.removeTaskByTriggerName");
					query.setParameter("triggerName",triggerName);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

   /**
    * Получить таск по имени операции
    * @param operationName имя операции
    * @return
    * @throws BusinessException
    */
	public PereodicalTask getTaskByOperationName(final String operationName) throws BusinessException
	{   /*пока что возвращает уникальный тасr, в последствии будет реализована множественность*/
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PereodicalTask>()
			{
				public PereodicalTask run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pereodicalTask.PereodicalTask.getTaskByOperationName");
					query.setParameter("operationName",operationName);
					return (PereodicalTask)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список результатов по таску
	 * @param task переодическая задача
	 * @return
	 * @throws BusinessException
	 */
	public List<TaskResult> getResultByTask(final PereodicalTask task) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<TaskResult>>()
			{
				public List<TaskResult> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult.getResultByTask");
					query.setParameter("task",task);
					return (List<TaskResult>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить максимальный результат по таску
	 * @param task переодическая задача
	 * @return
	 * @throws BusinessException
	 */
	public TaskResult getLastResultByTask(final PereodicalTask task) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<TaskResult>()
			{
				public TaskResult run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult.getLastResultByTask");
					query.setParameter("task",task);
					return (TaskResult)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список ошибок по результату
	 * @param result рещультат переодической задачи
	 * @return список ошибок
	 * @throws BusinessException
	 */
	public List<PereodicalTaskError> getErrorByResult(final TaskResult result) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PereodicalTaskError>>()
			{
				public List<PereodicalTaskError> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pereodicalTask.PereodicalTaskError.getErrorByResult");
					query.setParameter("result",result);
					return (List<PereodicalTaskError>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
	/**
	 * Обновление статуса периодической задачт (PereodicalTask и её наследники)
	 * @param task задача
	 * @param state статус, на который нужно обновить
	 * @throws BusinessException
	 */
	public void updatePeriodicalTaskStatus(final PereodicalTask task, final TaskState state) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					// обновляем сущность в базе
					Query query = session.getNamedQuery(task.getClass().getName() + ".updateStatus");
					query.setParameter("taskId", task.getId());
					query.setParameter("state", state.toString());
					query.executeUpdate();

					// обновляем сущность в рантайме
					task.setState(state);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
