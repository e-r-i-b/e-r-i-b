package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * Исключение, связанное с ограничениями на логины.
 */
public class LoginRestrictionException extends RestrictionException
{
	public LoginRestrictionException(String message)
	{
		super(message);
	}

	public LoginRestrictionException(Exception cause)
	{
		super(cause);
	}

	public LoginRestrictionException(String message, Exception cause)
	{
		super(message, cause);
	}
}
