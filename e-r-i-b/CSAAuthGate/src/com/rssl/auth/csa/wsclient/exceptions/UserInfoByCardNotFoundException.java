package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - информация о пользователе по номеру карты не найдена
 */
public class UserInfoByCardNotFoundException extends BackLogicException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public UserInfoByCardNotFoundException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ошибка
	 */
	public UserInfoByCardNotFoundException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause ошибка
	 */
	public UserInfoByCardNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
