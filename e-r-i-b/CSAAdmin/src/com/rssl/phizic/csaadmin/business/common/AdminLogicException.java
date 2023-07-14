package com.rssl.phizic.csaadmin.business.common;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author mihaylov
 * @ created 13.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Логическое исключение модуля CSAAdmin
 */
public class AdminLogicException extends LogicException
{
	private final long errorCode = 100;

	/**
	 * конструктор по сообщению
	 * @param message сообщение
	 */
	public AdminLogicException(String message)
	{
		super(message);
	}

	/**
	 * конструктор
	 * @param message сообщение
	 * @param cause исключение, породившее данное
	 */
	public AdminLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @return код ошибки
	 */
	public long getErrorCode()
	{
		return errorCode;
	}
}
