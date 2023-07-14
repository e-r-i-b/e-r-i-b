package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, взаимодействие с ЕРМБ возможно только по активному коннектору (по основному телефону)
 */
public class PhoneRegistrationNotActiveException extends LogicException
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
}
