package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class UserAlreadyRegisteredException extends RestrictionException
{
	public UserAlreadyRegisteredException(Exception cause)
	{
		super(cause);
	}

	public UserAlreadyRegisteredException(String message, Exception cause)
	{
		super(message, cause);
	}

	public UserAlreadyRegisteredException(String message)
	{
		super(message);
	}
}
