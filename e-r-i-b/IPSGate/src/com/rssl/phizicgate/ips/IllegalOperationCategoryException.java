package com.rssl.phizicgate.ips;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Erkin
 * @ created 11.08.2011
 * @ $Author$
 * @ $Revision$
 */
class IllegalOperationCategoryException extends GateException
{
	IllegalOperationCategoryException()
	{
	}

	IllegalOperationCategoryException(String message)
	{
		super(message);
	}

	IllegalOperationCategoryException(Throwable cause)
	{
		super(cause);
	}

	IllegalOperationCategoryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
