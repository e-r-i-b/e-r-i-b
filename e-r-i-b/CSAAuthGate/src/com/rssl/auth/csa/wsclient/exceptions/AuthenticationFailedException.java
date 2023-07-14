package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Исключение, сигнализирующее об ошибке аутентификации (неверное имя/пароль)
 * @author Pankin
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class AuthenticationFailedException extends BackLogicException
{
	public AuthenticationFailedException()
	{
	}

	public AuthenticationFailedException(String message)
	{
		super(message);
	}

	public AuthenticationFailedException(Throwable cause)
	{
		super(cause);
	}

	public AuthenticationFailedException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
