package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 19.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, взаимодействие с ЕРМБ возможно только по активному коннектору (по основному телефону)
 */
public class PhoneRegistrationNotActiveException extends BackLogicException
{
	/**
	 * ctor
	 * @param cause exception
	 */
	public PhoneRegistrationNotActiveException(Exception cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public PhoneRegistrationNotActiveException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param cause exception
	 */
	public PhoneRegistrationNotActiveException(String message, Exception cause)
	{
		super(message, cause);
	}
}
