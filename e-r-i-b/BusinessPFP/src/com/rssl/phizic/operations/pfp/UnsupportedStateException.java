package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 04.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * »сключение при инициализации операции прохождени€ ѕ‘ѕ не соответствующим статусом
 */
public class UnsupportedStateException extends BusinessException
{
	private final String realStatePath;

	/**
	 * @param message сообщение исключени€
	 * @param realStatePath путь который соответствует текущему статусу планировани€
	 */
	public UnsupportedStateException(String message, String realStatePath)
	{
		super(message);
		this.realStatePath = realStatePath;
	}

	/**
	 * @param cause исключение-источник
	 * @param realStatePath путь который соответствует текущему статусу планировани€
	 */
	public UnsupportedStateException(Throwable cause, String realStatePath)
	{
		super(cause);
		this.realStatePath = realStatePath;
	}

	/**
	 * @param message сообщение исключени€
	 * @param cause исключение-источник
	 * @param realStatePath путь который соответствует текущему статусу планировани€
	 */
	public UnsupportedStateException(String message, Throwable cause, String realStatePath)
	{
		super(message, cause);
		this.realStatePath = realStatePath;
	}

	/**
	 * @return путь который соответствует текущему статусу планировани€
	 */
	public String getRealStatePath()
	{
		return realStatePath;
	}
}
