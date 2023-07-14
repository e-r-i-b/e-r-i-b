package com.rssl.auth.csa.front.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author niculichev
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class FrontException extends SystemException
{
    public FrontException()
    {
    }

	public FrontException(String message)
	{
		super(message);
	}

	public FrontException(Throwable cause)
	{
       super(cause);
    }

	public FrontException(String message, Throwable cause)
	{
	   super(message, cause);
	}
}
