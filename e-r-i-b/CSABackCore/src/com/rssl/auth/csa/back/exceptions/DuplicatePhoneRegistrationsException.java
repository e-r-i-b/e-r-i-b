package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - номера телефонов уже зарегистрированы
 */
public class DuplicatePhoneRegistrationsException extends LogicException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public DuplicatePhoneRegistrationsException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ошибка
	 */
	public DuplicatePhoneRegistrationsException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause ошибка
	 */
	public DuplicatePhoneRegistrationsException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
