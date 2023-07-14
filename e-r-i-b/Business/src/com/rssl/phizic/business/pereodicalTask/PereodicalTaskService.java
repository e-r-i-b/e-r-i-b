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
 * ������ ��� ������ � ��������������  ��������, � ���� ��� � ���� ��������.
 */
public class PereodicalTaskService
{
	private static final SimpleService simpleService = new SimpleService();
    private static final CounterService counterService = new CounterService();

	private static final String TRIGGER_PREFIX = "PEREODICAL_";
	private static final String UNLOAD_JOB_NAME = "PereodicalJob"; // ����� ����� ������������� �����.
	private static final String UNLOAD_JOB_CLASS_NAME = "com.rssl.phizic.unloading.PereodicalJob"; // ����� ����� ������������� �����.
	private static final String UNLOAD_JOB_GROUP = "PereodicalGroup"; // ��� ������.
	private static final Long MILSEK_HOUR = 3600000L;

	private static final String NO_TIME_EX = "�� ������� �� cronExp, �� timeInterval";

	private final SimpleJobService jobService = new SimpleJobService();

	/**
	 * �������� ������� ������
	 * @param task ������ ��� ����������
	 * @throws BusinessException
	 */
	public void addOrUpdate(PereodicalTask task) throws BusinessException
	{
		simpleService.addOrUpdate(task);
	}

	/**
	 * ��������� ����� �������������� �������, ���������� ���������� ��� ��������
	 * @param task
	 * @throws BusinessException
	 */
	@Transactional
	public PereodicalTask addTask(PereodicalTask task) throws BusinessException
	{
		task.setTriggerName(getTriggerUniqueName());
		task.setState(TaskState.PERIODICAL);
		task.setCreationDate(Calendar.getInstance());
		//���� ������������ ���� ��������� �� ��������� cronTrigger, � ��������� ������ simpleTrigger,
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
	 * ��������� ����, � ����������� �� ���� ��� �� ������� timeInterval ���  cronExp
     * � ���� ����� ���������� ���� SimpleTrigger ���� CronTrigger, ������ �������(�� ����� � �����) ����� ������
	 * ����������: � ����� ��� ������ ���� �������, ����� ����� ������� �� ��������� 
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
			   /*�������� ����������*/
				long hourInMillis =  MILSEK_HOUR * timeInterval;
				/*���� ������� �������*/
				Date triggerStartTime = new Date(System.currentTimeMillis() + hourInMillis); 
			    trigger = new SimpleTrigger(triggerName, UNLOAD_JOB_GROUP, UNLOAD_JOB_NAME, UNLOAD_JOB_GROUP, new Date(), null, -1, 10000l);
			 }
			 else
				throw new BusinessException(NO_TIME_EX);

			if (jobService.isTriggerExist(UNLOAD_JOB_NAME,UNLOAD_JOB_GROUP,triggerName))
				/*��������� �������*/
				jobService.rescheduleJob(task.getTriggerName(),UNLOAD_JOB_GROUP,trigger);
			else
			{   /*��� ��������� �����*/
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
		 /*��������� ����*/
		simpleService.addOrUpdate(task);
	}

	/**
	 *  ���������������� ������ �������
	 * (��������� ��������, ��������� DISABLED)
	 * @param task ������� ��� ������������
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
	 * �������� ���������� ��� ��������
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
	 * �������� ���� �� ����� ��������
	 * @param triggerName  ��� ��������
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
	 * ������� ���� �� ����� ��������.
	 * @param triggerName ��� ��������
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
    * �������� ���� �� ����� ��������
    * @param operationName ��� ��������
    * @return
    * @throws BusinessException
    */
	public PereodicalTask getTaskByOperationName(final String operationName) throws BusinessException
	{   /*���� ��� ���������� ���������� ���r, � ����������� ����� ����������� ���������������*/
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
	 * �������� ������ ����������� �� �����
	 * @param task ������������� ������
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
	 * �������� ������������ ��������� �� �����
	 * @param task ������������� ������
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
	 * �������� ������ ������ �� ����������
	 * @param result ��������� ������������� ������
	 * @return ������ ������
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
	 * ���������� ������� ������������� ������ (PereodicalTask � � ����������)
	 * @param task ������
	 * @param state ������, �� ������� ����� ��������
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
					// ��������� �������� � ����
					Query query = session.getNamedQuery(task.getClass().getName() + ".updateStatus");
					query.setParameter("taskId", task.getId());
					query.setParameter("state", state.toString());
					query.executeUpdate();

					// ��������� �������� � ��������
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
