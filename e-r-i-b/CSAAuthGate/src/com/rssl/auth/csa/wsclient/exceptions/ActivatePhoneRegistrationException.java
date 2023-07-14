package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение - ошибка активации коннектора (коннектор не найден или найдено более одного коннектора по номеру телефона)
 */
public class ActivatePhoneRegistrationException extends BackLogicException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public ActivatePhoneRegistrationException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ошибка
	 */
	public ActivatePhoneRegistrationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause ошибка
	 */
	public ActivatePhoneRegistrationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
