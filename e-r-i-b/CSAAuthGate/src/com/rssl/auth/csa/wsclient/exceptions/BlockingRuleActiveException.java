package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, означающие, что включено правило блокировки - действуют ограничение на вход и регистрацию
 */
@SuppressWarnings("UnusedDeclaration")
public class BlockingRuleActiveException extends RestrictionViolatedException
{
	private final String date;
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public BlockingRuleActiveException(String message)
	{
		super(message);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param date дата снятия блокировки
	 */
	public BlockingRuleActiveException(String message, String date)
	{
		super(message);
		this.date = date;
	}


	/**
	 * ctor
	 * @param cause ошибка
	 */
	public BlockingRuleActiveException(Exception cause)
	{
		super(cause);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause ошибка
	 */
	public BlockingRuleActiveException(String message, Exception cause)
	{
		super(message, cause);
		this.date = null;
	}

	/**
	 * @return дата снятия блокировки
	 */
	public String getDate()
	{
		return date;
	}
}
