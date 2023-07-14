package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение неправильно введенного логина или пароля
 *
 * @author khudyakov
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginOrPasswordWrongLoginExeption extends BusinessLogicException
{
	private static final String MESSAGE = "Ошибка регистрации. Доступ в систему запрещен. Неправильно введен логин или пароль.";

	public LoginOrPasswordWrongLoginExeption()
	{
		this(MESSAGE);
	}

	public LoginOrPasswordWrongLoginExeption(String message)
	{
		super(message);
	}

	public LoginOrPasswordWrongLoginExeption(Throwable cause)
	{
		super(cause);
	}

	public LoginOrPasswordWrongLoginExeption(String message, Throwable cause)
	{
		super(message, cause);
	}
}
