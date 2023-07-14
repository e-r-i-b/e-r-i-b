package com.rssl.auth.csa.back.exceptions;

/**
 * @author osminin
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, телефон уже зарегистрирован
 */
public class PhoneAlreadyRegisteredException extends RestrictionException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public PhoneAlreadyRegisteredException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause причина
	 */
	public PhoneAlreadyRegisteredException(Exception cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause причина
	 */
	public PhoneAlreadyRegisteredException(String message, Exception cause)
	{
		super(message, cause);
	}
}
