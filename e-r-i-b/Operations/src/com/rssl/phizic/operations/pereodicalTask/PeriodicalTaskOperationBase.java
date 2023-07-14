package com.rssl.phizic.operations.pereodicalTask;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.pereodicalTask.PereodicalTask;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 17.02.2014
 * @ $Author$
 * @ $Revision$
 */

public abstract class PeriodicalTaskOperationBase<T extends PereodicalTask, R extends Restriction> extends OperationBase<R>
{
	protected static final SimpleService simpleService = new SimpleService();

	/**
	 * ������������������� �������� ������� �� ������� ������
	 * @param backroundTask ������� ������
	 */
	public abstract void initialize(T backroundTask) throws BusinessException, BusinessLogicException;

	/**
	 * ������� � ���������������� ������� ������.
	 * ��� ���������������� ���������, ����������� ��� ���������� ������ ������ ������� ��
	 * ��������������������� �������� � ����������� � ������
	 * @return ������� ������, �������������������� ����������������� �������.
	 */
	public abstract T createBackroundTask() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ��������(��������).
	 * ������ ����� ������������� ������ ������ ����������� �������� � ������������ ��� ��� ������,
	 * ��� � ��� ������� ����������.
	 * @return ��������� ���������� ��������
	 */
	public abstract PereodicalTaskResult execute() throws BusinessException, BusinessLogicException;

	/**
	 * ���������������� ������� ������
	 * @param backgroundTask ������� ������
	 * @return ������������������ ������� ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask) throws BusinessException
	{
		return registerBackgroundTask(backgroundTask, null);
	}

	/**
	 * ���������������� ������� ������
	 * @param backgroundTask ������� ������
	 * @param instanceName ��� ��������� ��
	 * @return ������������������ ������� ������
	 * @throws BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask, String instanceName) throws BusinessException
	{
		return simpleService.addOrUpdate(backgroundTask, instanceName);
	}
}
