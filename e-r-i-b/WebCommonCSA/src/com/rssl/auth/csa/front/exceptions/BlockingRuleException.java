package com.rssl.auth.csa.front.exceptions;

/**
 * @author osminin
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - действует правило блокировки, вход и регистрация запрещены
 */
public class BlockingRuleException extends InterruptStageException
{
	private static final String GLOBAL_KEY = "message.global.bloking.login";
	private static final String BLOCKING_RULE_KEY = "blocking.rule.message";
	private final String date;

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public BlockingRuleException(String message)
	{
		super(message);
		this.date = null;
	}

	/**
	 * ctor
	 * @param cause ошибка
	 */
	public BlockingRuleException(Throwable cause)
	{
		super(cause);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message сообщений об ошибке
	 * @param cause ошибка
	 */
	public BlockingRuleException(String message, Throwable cause)
	{
		super(message, cause);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message сообщений об ошибке
	 * @param date дата снятия блокировки
	 * @param cause ошибка
	 */
	public BlockingRuleException(String message, String date, Throwable cause)
	{
		super(message, cause);
		this.date = date;
	}

	/**
	 * @return дата снятия блокировки
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * @return ключ текстовки
	 */
	public String getErrorKey()
	{
		return date == null ? GLOBAL_KEY : BLOCKING_RULE_KEY;
	}
}
