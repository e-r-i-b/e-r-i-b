package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author akrenev
 * @ created 13.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка взаимодействия с деловой средой
 */

public class BusinessEnvironmentException extends BackException
{
	/**
	 * конструктор
	 * @param message сообщение
	 */
	public BusinessEnvironmentException(String message)
	{
		super(message);
	}
}
