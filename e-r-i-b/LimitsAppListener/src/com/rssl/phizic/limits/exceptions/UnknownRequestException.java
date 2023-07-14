package com.rssl.phizic.limits.exceptions;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - неизвестный тип запроса
 */
public class UnknownRequestException extends Exception
{
	/**
	 * ctor
	 */
	public UnknownRequestException()
	{}

	/**
	 * ctor
	 * @param cause причина
	 */
	public UnknownRequestException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param error сообщение об ошибка
	 */
	public UnknownRequestException(String error)
	{
		super(error);
	}

	/**
	 * ctor
	 * @param error сообщение об ошибке
	 * @param cause причина
	 */
	public UnknownRequestException(String error, Throwable cause)
	{
		super(error, cause);
	}
}
