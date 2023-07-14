package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginAlreadyRegisteredException extends LoginRestrictionException
{
	public LoginAlreadyRegisteredException(Exception cause)
	{
		super(cause);
	}

	public LoginAlreadyRegisteredException(String message)
	{
		super(message);
	}
}
