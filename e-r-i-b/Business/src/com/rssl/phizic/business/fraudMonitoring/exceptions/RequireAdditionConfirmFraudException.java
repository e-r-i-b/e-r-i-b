package com.rssl.phizic.business.fraudMonitoring.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение, требующее дополнительного подтверждения операции
 *
 * @author khudyakov
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RequireAdditionConfirmFraudException extends BusinessLogicException
{
	public RequireAdditionConfirmFraudException(String message)
	{
		super(message);
	}

	public RequireAdditionConfirmFraudException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
