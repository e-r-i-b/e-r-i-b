package com.rssl.phizicgate.ips;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Erkin
 * @ created 11.08.2011
 * @ $Author$
 * @ $Revision$
 */
class IllegalOperationTypeException extends GateException
{
	IllegalOperationTypeException()
	{
	}

	IllegalOperationTypeException(String message)
	{
		super(message);
	}

	IllegalOperationTypeException(Throwable cause)
	{
		super(cause);
	}

	IllegalOperationTypeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
