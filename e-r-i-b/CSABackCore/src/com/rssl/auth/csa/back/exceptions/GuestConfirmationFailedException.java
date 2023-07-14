package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.guest.GuestConfirmableOperation;
import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Ошибка подтверждения гостевой операции
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
		super("Гостевая операция " + operation.getOuid() + " не может быть подтверждена");
		this.operation = operation;
	}

	/**
	 * @return подтверджаемая операция
	 */
	public GuestConfirmableOperation getOperation()
	{
		return operation;
	}
}
