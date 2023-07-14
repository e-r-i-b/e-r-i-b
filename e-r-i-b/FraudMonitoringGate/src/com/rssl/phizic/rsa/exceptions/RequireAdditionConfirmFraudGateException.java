package com.rssl.phizic.rsa.exceptions;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.Constants;

/**
 * Исключение, требующее дополнительного подтверждения операции
 *
 * @author khudyakov
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RequireAdditionConfirmFraudGateException extends GateLogicException
{
	public RequireAdditionConfirmFraudGateException()
	{
		super(Constants.REQUIRED_ADDITIONAL_CONFIRM_DEFAULT_ERROR_MESSAGE);
	}

	public RequireAdditionConfirmFraudGateException(String message)
	{
		super(message);
	}

	public RequireAdditionConfirmFraudGateException(Throwable cause)
	{
		super(Constants.REQUIRED_ADDITIONAL_CONFIRM_DEFAULT_ERROR_MESSAGE, cause);
	}

	public RequireAdditionConfirmFraudGateException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
