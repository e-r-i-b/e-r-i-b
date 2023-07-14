package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmationFailedException extends LogicException
{
	private final ConfirmableOperationBase operaton;

	public ConfirmationFailedException(ConfirmableOperationBase operaton)
	{
		super("Операция " + operaton.getOuid() + " не может быть подтверждена");
		this.operaton = operaton;
	}

	public ConfirmableOperationBase getOperaton()
	{
		return operaton;
	}
}
