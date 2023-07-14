package com.rssl.phizic.csa.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */
public class CSAGetNodeInfoLogicException extends LogicException
{
	public CSAGetNodeInfoLogicException(String message)
	{
		super(message);
	}

	public CSAGetNodeInfoLogicException(Throwable cause)
	{
		super(cause);
	}

	public CSAGetNodeInfoLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
