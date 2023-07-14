package com.rssl.phizic.csa.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Логическое исключение при обработке смс-сообщения
 */
public class CSASmsProcessingLogicException extends LogicException
{
	private final String phoneNumber;

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param phoneNumber номер телефона
	 */
	public CSASmsProcessingLogicException(String message, String phoneNumber)
	{
		super(message);
		this.phoneNumber = phoneNumber;
	}

	/**
	 * ctor
	 * @param message сообщение об ошибке
	 * @param phoneNumber номер телефона
	 * @param cause исключение
	 */
	public CSASmsProcessingLogicException(String message, String phoneNumber, Throwable cause)
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
