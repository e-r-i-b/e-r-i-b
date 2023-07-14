package com.rssl.phizic.csa.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение при обработке смс-сообщения
 */
public class CSASmsProcessingException extends SystemException
{
	private final String phoneNumber;

	/**
	 * ctor
	 * @param cause исключение
	 * @param phoneNumber номер телефона
	 */
	public CSASmsProcessingException(Throwable cause, String phoneNumber)
	{
		super(cause);
		this.phoneNumber = phoneNumber;
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param phoneNumber номер телфона
	 * @param cause исключение
	 */
	public CSASmsProcessingException(String message, String phoneNumber, Throwable cause)
	{
		super(message, cause);
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return номер телефона
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
}
