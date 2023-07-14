package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - карта по номеру телефона не найдена
 */
public class CardByPhoneNotFoundException extends BackLogicException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public CardByPhoneNotFoundException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ошибка
	 */
	public CardByPhoneNotFoundException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause ошибка
	 */
	public CardByPhoneNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
