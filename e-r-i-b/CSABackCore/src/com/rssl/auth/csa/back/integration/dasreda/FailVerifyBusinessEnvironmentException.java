package com.rssl.auth.csa.back.integration.dasreda;

/**
 * @author akrenev
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Исключение ошибочного ответа от деловой среды
 */

public class FailVerifyBusinessEnvironmentException extends BusinessEnvironmentException
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
