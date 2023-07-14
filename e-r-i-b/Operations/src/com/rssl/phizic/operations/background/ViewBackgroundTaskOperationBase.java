package com.rssl.phizic.operations.background;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.business.operations.background.TaskResult;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author krenev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class ViewBackgroundTaskOperationBase<TR extends TaskResult, T extends BackgroundTask<TR>> extends OperationBase implements ViewEntityOperation<T>
{
	private static final SimpleService simpleService = new SimpleService();
	private T entity;

	/**
	 * ���������������� ��������
	 * @param id ������������ ������� ������
	 */
	public void initialize(Long id) throws BusinessException
	{
		entity = simpleService.findById(getBackgroundTaskClass(), id, MultiBlockModeDictionaryHelper.getDBInstanceName());
		if (entity == null)
			throw new BusinessException("�� ������� ������� ������ � �������������� " + id);
	}

	/**
	 * @return ����� ���������� ������
	 */
	protected abstract Class<T> getBackgroundTaskClass();

	/**
	 *
	 * @return ������� ������
	 */
	public T getEntity() throws BusinessException, BusinessLogicException
	{
		return entity;
	}

}
