package com.rssl.phizicgate.mobilebank;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Неожиданная ошибка
 */
class UnexpectedErrorException extends RuntimeException
{
	UnexpectedErrorException() {}

	UnexpectedErrorException(String message)
	{
		super(message);
	}

	UnexpectedErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	UnexpectedErrorException(Throwable cause)
	{
		super(cause);
	}
}
