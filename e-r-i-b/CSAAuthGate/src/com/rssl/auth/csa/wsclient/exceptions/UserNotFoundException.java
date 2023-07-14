package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author gladishev
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - не найдена информация о пользователе
 */
public class UserNotFoundException extends BackException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public UserNotFoundException(String message)
	{
		super(message);
	}
}
