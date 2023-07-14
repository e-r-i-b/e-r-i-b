package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.guest.GuestConfirmableOperation;
import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * ������ ������������� �������� ��������
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestConfirmationFailedException extends LogicException
{
	private final GuestConfirmableOperation operation;

	public GuestConfirmationFailedException(GuestConfirmableOperation operation)
	{
		super("�������� �������� " + operation.getOuid() + " �� ����� ���� ������������");
		this.operation = operation;
	}

	/**
	 * @return �������������� ��������
	 */
	public GuestConfirmableOperation getOperation()
	{
		return operation;
	}
}
