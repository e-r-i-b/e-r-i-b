package com.rssl.phizic.business.migration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с состоянием миграции
 */

public class MigrationInfoService
{
	private static final SimpleService service = new SimpleService();

	private TotalMigrationInfo getInfo(MigrationTask task)
	{
		if (task == null)
			return null;

		Calendar start = null;
		Calendar end = null;
		int goodCount = 0;
		int badCount = 0;
		MigrationState state = MigrationState.INITIALIZE;
		int stopCount = 0;

		for (MigrationThreadTask threadTask : task.getThreadTasks())
		{
			if (start == null)
				start = threadTask.getStartDate();

			if (end == null || threadTask.getEndDate() != null && end.compareTo(threadTask.getEndDate()) < 0)
				end = threadTask.getEndDate();

			goodCount += threadTask.getGoodCount();
			badCount += threadTask.getBadCount();

			switch (threadTask.getState())
			{
				case STOP: stopCount++; break;
				case WAIT: if (state == MigrationState.PROCESS) break;
				default: state = threadTask.getState();
			}
		}

		if (stopCount > 0 && stopCount == task.getThreadTasks().size())
			state = MigrationState.STOP;
		else
			end = null;

		return new TotalMigrationInfo(task.getId(), start, end, task.getTotalCount(), goodCount, badCount, state, task.getBatchSize());
	}

	/**
	 * @return информация о миграции
	 */
	public TotalMigrationInfo getInfo() throws BusinessException
	{
		try
		{
			Long taskId = HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					/*
					    Получение идентификатора актуальной задачи миграции
					    Опорный объект: PK_MIGRATION_INFO
					    Предикаты доступа: -
					    Кардинальность: 1
					*/
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MigrationTask.class);
					detachedCriteria.setProjection(Projections.max("id"));
					Criteria criteria = detachedCriteria.getExecutableCriteria(session);
					return (Long) criteria.uniqueResult();
				}
			});
			return getInfo(taskId);
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения состояния миграции.", e);
		}
	}

	/**
	 * @param id идентификатор задачи
	 * @return информация о миграции
	 */
	public TotalMigrationInfo getInfo(Long id) throws BusinessException
	{
		MigrationTask task = service.findById(MigrationTask.class, id);
		return getInfo(task);
	}

	private MigrationTask getInitializedMigrationTask(Session session, Long taskId)
	{
		/*
		    Получение идентификатора актуальной задачи миграции
		    Опорный объект: PK_MIGRATION_INFO
		    Предикаты доступа: access("THIS_"."ID">=TO_NUMBER(:ID))
		    Кардинальность: 1 (больше в случае, если работа идет в многопоточке и данные одного потока очень сильно устарели)
		*/
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MigrationTask.class);
		detachedCriteria.add(Expression.ge("id", taskId == null? 0L: taskId));
		detachedCriteria.addOrder(Order.desc("id"));
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		criteria.setLockMode(LockMode.UPGRADE_NOWAIT);
		//noinspection unchecked
		List<MigrationTask> result = criteria.list();
		MigrationTask task = CollectionUtils.isEmpty(result)? null: result.get(0);

		if (task == null || getInfo(task).getState() == MigrationState.STOP)
		{
			MigrationTask migrationTask = new MigrationTask();
			migrationTask.setTotalCount(null);
			migrationTask.setNeedStop(false);
			migrationTask.setBatchSize(null);
			migrationTask.setThreadTasks(new ArrayList<MigrationThreadTask>());
			session.save(migrationTask);
			task = migrationTask;
		}
		return task;
	}

	/**
	 * создание задачи на миграцию
	 * @param oldId идентификатор предыдущей задачи
	 * @param totalClientsCount общее количество клиентов для миграции
	 * @return информация о миграции
	 * @throws BusinessException
	 */
	public TotalMigrationInfo createMigrationTask(final Long oldId, final Long totalClientsCount) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<TotalMigrationInfo>()
			{
				public TotalMigrationInfo run(Session session) throws Exception
				{
					MigrationTask task = getInitializedMigrationTask(session, oldId);
					if (CollectionUtils.isEmpty(task.getThreadTasks()))
						task.setTotalCount(totalClientsCount);

					return getInfo(task);
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Ошибка инициализации процесса миграции.", e);
		}
		catch (LockAcquisitionException e)
		{
			throw new BusinessLogicException("Ошибка инициализации процесса миграции.", e);
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка запуска миграции.", e);
		}
	}

	/**
	 * создать задачу на запуск процесса миграции
	 * @param id идентификатор задачи
	 * @param batchSize размер пачки
	 * @param taskCount количество задач
	 * @return информация о миграции
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public TotalMigrationInfo startMigration(final Long id, final long batchSize, final int taskCount) throws BusinessException, BusinessLogicException
	{
		try
		{
			MigrationTask task = HibernateExecutor.getInstance().execute(new HibernateAction<MigrationTask>()
			{
				public MigrationTask run(Session session) throws Exception
				{
					MigrationTask task = getInitializedMigrationTask(session, id);

					if (CollectionUtils.isNotEmpty(task.getThreadTasks()))
						throw new BusinessLogicException("Миграция уже запущена!");

					Calendar startDate = Calendar.getInstance();
					List<MigrationThreadTask> threadTasks = task.getThreadTasks();

					for (int i = 0; i< taskCount; i++)
					{
						MigrationThreadTask threadTask = new MigrationThreadTask();
						threadTask.setState(MigrationState.WAIT);
						threadTask.setStartDate(startDate);
						threadTask.setEndDate(null);
						threadTask.setGoodCount(0);
						threadTask.setBadCount(0);
						threadTasks.add(threadTask);
					}

					task.setBatchSize(batchSize);
					session.save(task);
					return task;
				}
			});
			return getInfo(task);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Ошибка инициализации процесса миграции.", e);
		}
		catch (LockAcquisitionException e)
		{
			throw new BusinessLogicException("Ошибка инициализации процесса миграции.", e);
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка запуска миграции.", e);
		}
	}

	/**
	 * создать задачу на остановку процесса миграции
	 * @param id идентификатор задачи
	 * @return информация о миграции
	 * @throws BusinessException
	 */
	public TotalMigrationInfo stopMigration(final Long id) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.migration.MigrationTask.stop");
					query.setParameter("id", id);
					query.executeUpdate();
					return null;
				}
			});
			return getInfo(id);
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка остановки миграции.", e);
		}
	}

	private ThreadMigrationInfo getMigrationParameters(Long threadId, Session session)
	{
		Query query = session.getNamedQuery("com.rssl.phizic.business.migration.ThreadMigrationInfo.getInfo");
		query.setParameter("id", threadId, Hibernate.LONG);
		return (ThreadMigrationInfo) query.uniqueResult();
	}

	/**
	 * @return добавить информацию о миграции текущего потока
	 */
	public ThreadMigrationInfo startThreadMigration() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ThreadMigrationInfo>()
			{
				public ThreadMigrationInfo run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.migration.MigrationThreadTask.getWaiting");
					query.setLockMode("migrationThreadTask", LockMode.UPGRADE_NOWAIT);
					MigrationThreadTask threadTask = (MigrationThreadTask) query.uniqueResult();
					if (threadTask == null)
						return null;

					ThreadMigrationInfo info = getMigrationParameters(threadTask.getId(), session);
					threadTask.setState(info.isNeedStop()? MigrationState.STOP: MigrationState.PROCESS);
					session.save(threadTask);
					return info;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка запуска потока миграции.", e);
		}
	}

	/**
	 * остановить процесс миграции потока
	 * @param threadId идентификатор потока
	 * @throws BusinessException
	 */
	public void stopThreadMigration(final Long threadId) throws BusinessException
	{
		MigrationThreadTask threadTask = service.findById(MigrationThreadTask.class, threadId);
		threadTask.setState(MigrationState.STOP);
		threadTask.setEndDate(Calendar.getInstance());
		service.update(threadTask);
	}

	/**
	 * получить информацию о миграции для потока
	 * @param threadId идентификатор потока
	 * @return информация
	 */
	public ThreadMigrationInfo getThreadMigrationInfo(final Long threadId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ThreadMigrationInfo>()
			{
				public ThreadMigrationInfo run(Session session) throws Exception
				{
					return getMigrationParameters(threadId, session);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка запуска потока миграции.", e);
		}
	}
}
