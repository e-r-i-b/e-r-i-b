package com.rssl.phizic.limits.exceptions;

/**
 * @author osminin
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - транзакция не найдена
 */
public class TransactionNotFoundException extends Exception
{
	/**
	 * ctor
	 */
	public TransactionNotFoundException()
	{}

	/**
	 * ctor
	 * @param cause причина
	 */
	public TransactionNotFoundException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param error сообщение об ошибка
	 */
	public TransactionNotFoundException(String error)
	{
		super(error);
	}

	/**
	 * ctor
	 * @param error сообщение об ошибке
	 * @param cause причина
	 */
	public TransactionNotFoundException(String error, Throwable cause)
	{
		super(error, cause);
	}
}
