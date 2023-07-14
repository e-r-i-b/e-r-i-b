package com.rssl.phizic.business.operations;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Kosyakova
 * @ created 20.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class OperationClassNotFoundException extends BusinessException
{
	public OperationClassNotFoundException(String message)
	{
		super(message);
	}

	public OperationClassNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public OperationClassNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
