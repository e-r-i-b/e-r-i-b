package com.rssl.phizic.csaadmin.business.common;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение модуля CSAAdmin
 */

public class AdminException extends IKFLException
{
	/**
	 * дефолтный конструктор
	 */
	public AdminException()
	{}

	/**
	 * конструктор по сообщению
	 * @param message сообщение
	 */
	public AdminException(String message)
	{
		super(message);
	}

	/**
	 * конструктор по исключению
	 * @param cause исключение
	 */
	public AdminException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * конструктор по сообщению и исключению
	 * @param message сообщение
	 * @param cause исключение
	 */
	public AdminException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
