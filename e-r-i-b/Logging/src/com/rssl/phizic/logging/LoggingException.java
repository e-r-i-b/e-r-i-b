package com.rssl.phizic.logging;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author Erkin
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение подсистемы логирования
 */
public class LoggingException extends SystemException
{
	public LoggingException()
	{
	}

	public LoggingException(String message)
	{
		super(message);
	}

	public LoggingException(Throwable cause)
	{
		super(cause);
	}

	public LoggingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
