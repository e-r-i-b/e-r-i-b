package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение поиска клиента
 */

public class ClientNotFoundException extends LogicException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public ClientNotFoundException(String message)
	{
		super(message);
	}

	/**
	 * конструктор
	 * @param cause породившее исключение
	 */
	public ClientNotFoundException(Throwable cause)
	{
		super(cause);
	}
}
