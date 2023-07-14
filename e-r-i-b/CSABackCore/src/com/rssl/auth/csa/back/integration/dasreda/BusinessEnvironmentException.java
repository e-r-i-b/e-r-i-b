package com.rssl.auth.csa.back.integration.dasreda;

/**
 * @author akrenev
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * Исключение взаимодействия с деловой средой
 */

public class BusinessEnvironmentException extends Exception
{
	/**
	 * конструктор
	 * @param cause исключение-инициатор
	 */
	public BusinessEnvironmentException(Exception cause)
	{
		super(cause);
	}

	/**
	 * конструктор
	 * @param message сообщение
	 */
	public BusinessEnvironmentException(String message)
	{
		super(message);
	}
}
