package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;

/**
 * @author krenev
 * @ created 19.11.2012
 * @ $Author$
 * @ $Revision$
 * ������� ����� ����������� �� ���������� ���������������� ������ ����������� ����
 */
public abstract class RequestCountRestrictionBase<T extends Operation> implements OperationRestriction<T>
{
	public void check(T operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		if (operation.getClass() != getOperationClass())
		{
			throw new IllegalArgumentException("�������� ��� ��������: " + operation.getClass() + ". ���������: " + getOperationClass());
		}
		Long profileId = operation.getProfileId();
		int maxRequestCount = getMaxRequestCount();
		int requestCheckInterval = getRequestCheckInterval();
		int count = Operation.getCount(profileId, getOperationClass(), requestCheckInterval, OperationState.NEW, OperationState.REFUSED);
		if (count > maxRequestCount)
		{
			throw new TooManyRequestException(profileId);
		}
	}

	/**
	 * @return ����� �������������� ������.
	 */
	protected abstract Class<T> getOperationClass();

	/**
	 * @return ���������� ������ (������� ������, ������� �� �������� �������) ��� �������� ���������� ���������������� ��������
	 */
	protected abstract int getRequestCheckInterval();

	/**
	 * @return ����������� ���������� ���������� ���������������� �������� �� getRequestCheckInterval ���
	 */
	protected abstract int getMaxRequestCount();
}
