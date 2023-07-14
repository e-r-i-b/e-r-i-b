package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author akrenev
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Исключение ошибочного ответа от деловой среды
 */

public class FailVerifyBusinessEnvironmentException extends BackLogicException
{
	/**
	 * конструктор
	 * @param message сообщение
	 */
	public FailVerifyBusinessEnvironmentException(String message)
	{
		super(message);
	}
}
