package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTask;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTaskContent;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mihaylov
 * @ created 06.02.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceBackgroundTaskService
{
	protected static final MultiInstanceSimpleService multiInstanceSimpleService = new MultiInstanceSimpleService();

	/**
	 * �������� ��������� ������� ������
	 * @param backgroundTaskClass - ����� ������� ������
	 * @param instanceName ������� ��
	 * @return ������� ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <T extends BackgroundTask> T getNextTask(final Class<T> backgroundTaskClass, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(backgroundTaskClass);
					criteria.add(Restrictions.eq("state", TaskState.NEW));
					criteria.addOrder(Order.asc("creationDate"));
					criteria.setMaxResults(1);
					return (T) criteria.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������� ������� ������
	 * @param task ������
	 * @param state ������, �� ������� ����� ��������
	 * @param report ��������� � �����
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 */
	public void updateStatus(final BackgroundTaskBase task, final TaskState state, final String report, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					// ��������� �������� � ����
					Query query = session.getNamedQuery(task.getClass().getName() + ".updateStatus");
					query.setParameter("taskId", task.getId());
					query.setParameter("state", state.toString());
					query.setParameter("report", report);
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

	/**
	 * ���������� ������� ������������� ������ (PereodicalTask � � ����������)
	 * @param task ������
	 * @param state ������, �� ������� ����� ��������
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 */
	public void updatePeriodicalTaskStatus(final BackgroundTaskBase task, final TaskState state, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
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

	/**
	 * �������� ��� �������� ������� ������
	 * @param task ������ ��� ����������
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 */
	public void addOrUpdate(BackgroundTask task, String instanceName) throws BusinessException
	{
		multiInstanceSimpleService.addOrUpdate(task,instanceName);
	}

	/**
	 * ������� ������� ������
	 * @param task ������� ������ ��� ��������
	 * @param instanceName ������� ��
	 * @throws BusinessException
	 */
	public void remove(BackgroundTask task, String instanceName) throws BusinessException
	{
		multiInstanceSimpleService.remove(task,instanceName);
	}

	/**
	 * ����� ������� ������ �� ��������������
	 * @param clazz ����� ������� ������
	 * @param id ������������� ������� ������
	 * @param instanceName ������� ��
	 * @return ��������� ������� ������
	 * @throws BusinessException
	 */
	public <T extends BackgroundTask> T findById(Class<T> clazz, Long id, String instanceName) throws BusinessException
	{
		return multiInstanceSimpleService.findById(clazz, id, instanceName);
	}

	/**
	 * �������� ���������� ��� ������� ������
	 * @param backgroundTask - ������� ������
	 * @param stream - ����� � �������
	 * @param instanceName - ������� �� � ������� ���������� �������� ���������� ������
	 * @throws BusinessException
	 */
	public void addBackgroundTaskContent(ReplicateDepartmentsBackgroundTask backgroundTask, InputStream stream, String instanceName) throws BusinessException
	{
		try
		{
			ReplicateDepartmentsBackgroundTaskContent taskContent = new ReplicateDepartmentsBackgroundTaskContent(backgroundTask.getId(), Hibernate.createBlob(stream));
			multiInstanceSimpleService.add(taskContent,instanceName);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}
}
