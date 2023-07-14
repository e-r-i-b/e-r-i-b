package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * Исключение, связанное с ограничениями на пароли.
 */
public class PasswordRestrictionException extends RestrictionException
{
	public PasswordRestrictionException(String message)
	{
		super(message);
	}

	public PasswordRestrictionException(Exception cause)
	{
		super(cause);
	}

	public PasswordRestrictionException(String message, Exception cause)
	{
		super(message, cause);
	}
}