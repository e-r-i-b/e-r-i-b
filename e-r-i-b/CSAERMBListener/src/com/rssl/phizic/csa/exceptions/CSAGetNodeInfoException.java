package com.rssl.phizic.csa.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author osminin
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */
public class CSAGetNodeInfoException extends SystemException
{
	public CSAGetNodeInfoException(String message)
	{
		super(message);
	}

	public CSAGetNodeInfoException(Throwable cause)
	{
		super(cause);
	}

	public CSAGetNodeInfoException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
