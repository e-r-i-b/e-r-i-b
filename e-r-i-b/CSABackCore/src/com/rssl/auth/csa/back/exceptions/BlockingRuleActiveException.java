package com.rssl.auth.csa.back.exceptions;

/**
 * @author osminin
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - действует правило блокировки
 */
@SuppressWarnings("UnusedDeclaration")
public class BlockingRuleActiveException extends RestrictionException
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
	 * ctor
	 * @param message сообщение об ошибке
	 * @param date дата окончания блокировки
	 */
	public BlockingRuleActiveException(String message, String date)
	{
		super(message);
		this.date = date;
	}

	/**
	 * @return дата окончания блокировки
	 */
	public String getDate()
	{
		return date;
	}
}
