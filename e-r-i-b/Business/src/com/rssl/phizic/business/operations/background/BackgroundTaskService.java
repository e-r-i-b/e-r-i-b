package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTaskContent;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 * @author niculichev
 * @ created 31.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackgroundTaskService extends MultiInstanceBackgroundTaskService
{

	/**
	 * �������� ��������� ������� ������
	 * @return ������� ������
	 * @throws BusinessException
	 */
	public <T extends BackgroundTask> T getNextTask(final Class<T> backgroundTaskClass) throws BusinessException
	{
		return super.getNextTask(backgroundTaskClass,null);
	}

	/**
	 * ���������� ������� ������� ������
	 * @param task ������
	 * @param state ������, �� ������� ����� ��������
	 * @param report ��������� � �����
	 * @throws BusinessException
	 */
	public void updateStatus(final BackgroundTaskBase task, final TaskState state, final String report) throws BusinessException
	{
		super.updateStatus(task,state,report,null);
	}

	/**
	 * ���������� ������� ������������� ������ (PereodicalTask � � ����������)
	 * @param task ������
	 * @param state ������, �� ������� ����� ��������
	 * @throws BusinessException
	 */
	public void updatePeriodicalTaskStatus(final BackgroundTaskBase task, final TaskState state) throws BusinessException
	{
		super.updatePeriodicalTaskStatus(task,state,null);
	}

	/**
	 * �������� ������� ������
	 * @param task ������ ��� ����������
	 * @throws BusinessException
	 */
	public void addOrUpdate(BackgroundTask task) throws BusinessException
	{
		super.addOrUpdate(task,null);
	}

	/**
	 * ������� ������� ������
	 * @param task ������� ������ ��� ��������
	 * @throws BusinessException
	 */
	public void remove(BackgroundTask task) throws BusinessException
	{
		super.remove(task, null);
	}

	/**
	 * ����� ������� ������ �� ��������������
	 * @param clazz ����� ������� ������
	 * @param id ������������� ������� ������
	 * @return ��������� ������� ������
	 * @throws BusinessException
	 */
	public <T extends BackgroundTask> T findById(Class<T> clazz, Long id) throws BusinessException
	{
		return super.findById(clazz,id,null);
	}

	/**
	 * �������� ���� ��� ���������� ������� ������
	 * @param taskId - ������������� ������� ������
	 * @return ���� ��� ���������� ������� ������
	 * @throws BusinessException
	 */
	public ReplicateDepartmentsBackgroundTaskContent getReplicaTaskContent(final Long taskId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ReplicateDepartmentsBackgroundTaskContent>()
			{
				public ReplicateDepartmentsBackgroundTaskContent run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(ReplicateDepartmentsBackgroundTaskContent.class);
					criteria.add(Expression.eq("replicateDepartmentTaskId", taskId));
					ReplicateDepartmentsBackgroundTaskContent taskContent = (ReplicateDepartmentsBackgroundTaskContent) criteria.uniqueResult();
					session.delete(taskContent);
					return  taskContent;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
