package com.rssl.phizic.business.fraudMonitoring.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение, запрещающее выполнение операции
 *
 * @author khudyakov
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ProhibitionOperationFraudException extends BusinessLogicException
{
	public ProhibitionOperationFraudException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
