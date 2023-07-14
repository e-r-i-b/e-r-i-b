package com.rssl.phizic.business.dictionaries.synchronization.information;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationEntity;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ��������� ��������� �������������
 */

public class SynchronizationStateService
{
	private static final int MAX_ERROR_COUNT = 5;
	private static final String QUERY_NAME_PREFIX       = "com.rssl.phizic.business.dictionaries.synchronization.information.";
	private static final String DO_WAIT_QUERY_NAME      = QUERY_NAME_PREFIX + "doWait";
	private static final String DO_PROCESS_QUERY_NAME   = QUERY_NAME_PREFIX + "doProcess";
	private static final String DO_UPDATE_QUERY_NAME    = QUERY_NAME_PREFIX + "doUpdate";
	private static final String DO_UPDATED_QUERY_NAME   = QUERY_NAME_PREFIX + "doUpdated";
	private static final String DO_ERROR_QUERY_NAME     = QUERY_NAME_PREFIX + "doError";

	private static final SimpleService service = new SimpleService();

	/**
	 * ��������� ������������� � ��������� "��������"
	 * @param synchronizationParameters ��������� �������������
	 * @throws BusinessException
	 */
	public void doWait(NotificationEntity synchronizationParameters) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), DO_WAIT_QUERY_NAME);
			executorQuery.setParameter("maxErrorCount",         MAX_ERROR_COUNT);
			executorQuery.setParameter("synchronizationMode",   synchronizationParameters.getMode().name());
			executorQuery.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������������� � ��������� "��������������"
	 * @return ������� �� �������� ������
	 * @throws BusinessException
	 */
	public boolean doProcess() throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), DO_PROCESS_QUERY_NAME);
			return executorQuery.executeUpdate() > 0;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ��������� ��������� ����������� ������
	 * @param lastUpdateId    ������������� ��������� ����������� ������
	 * @param lastUpdateDate  ���� ��������� ��������� ����������� ������
	 * @throws BusinessException
	 */
	public void doUpdate(Long lastUpdateId, Calendar lastUpdateDate) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), DO_UPDATE_QUERY_NAME);
			executorQuery.setParameter("lastUpdateId", lastUpdateId);
			executorQuery.setParameter("lastUpdateDate", lastUpdateDate);
			executorQuery.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������������� � ��������� "������" (DictionaryState.ERROR == endSate) ��� "��������" (DictionaryState.UPDATED == endSate)
	 * @param endSate �������� ��������� �������������
	 * @throws BusinessException
	 */
	public void doEnd(SynchronizationState endSate) throws BusinessException
	{
		try
		{
			String queryName;
			switch (endSate)
			{
				case UPDATED: queryName = DO_UPDATED_QUERY_NAME; break;
				case ERROR:   queryName = DO_ERROR_QUERY_NAME;   break;
				default: throw new BusinessException("����������� �������� ��������� ������������� " + endSate);
			}

			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), queryName);
			executorQuery.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ������ ������������� ������������
	 * @throws BusinessException
	 */
	public SynchronizationStateEntity getLastSynchronizationInfo() throws BusinessException
	{

		List<SynchronizationStateEntity> synchronizationStateEntities = service.getAll(SynchronizationStateEntity.class);
		if (synchronizationStateEntities.size() == 0)
			return null;

		return synchronizationStateEntities.get(0);
	}
}
