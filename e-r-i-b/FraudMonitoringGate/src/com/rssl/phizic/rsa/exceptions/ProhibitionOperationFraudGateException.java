package com.rssl.phizic.rsa.exceptions;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Исключение, запрещающее выполнение операции
 *
 * @author khudyakov
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ProhibitionOperationFraudGateException extends GateLogicException
{
	public ProhibitionOperationFraudGateException(String message)
	{
		super(message);
	}
}
