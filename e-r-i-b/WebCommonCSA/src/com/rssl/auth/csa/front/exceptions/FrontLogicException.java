package com.rssl.auth.csa.front.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author niculichev
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class FrontLogicException extends LogicException
{
	private static final String DEFAULT_MESSAGE = "Операция временно недоступна";

	public FrontLogicException()
	{
		super(DEFAULT_MESSAGE);
	}
	
	public FrontLogicException(String message)
	{
		super(message);
	}

	public FrontLogicException(Throwable cause)
	{
        super(cause);
    }

	public FrontLogicException(String message, Throwable cause)
	{
	    super(message, cause);
	}
}
