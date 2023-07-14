package com.rssl.phizic.common.types.exceptions;

/**
 * @author komarov
 * @ created 07.08.2014
 * @ $Author$
 * @ $Revision$
 * Сессия инвалидирована
 */
public class InvalidSessionException extends RuntimeException
{
	/**
	 * Конструктор
	 */
	public InvalidSessionException()
	{
		super();
	}

	/**
	 * @param message сообщение
	 */
	public InvalidSessionException(String message)
	{
		super(message);
	}

	/**
	 * @param message сообщение
	 * @param cause причина исключения
	 */
	public InvalidSessionException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param cause причина исключения
	 */
	public InvalidSessionException(Throwable cause)
	{
		super(cause);
	}
}
