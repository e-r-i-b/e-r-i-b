package com.rssl.common.forms;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * Логическая ошибка при работе с документом (ошибка пользователя)
 *
 * @author Evgrafov
 * @ created 21.11.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 72237 $
 */
public class DocumentLogicException extends IKFLException
{

	/** @param message сообщение */
	public DocumentLogicException(String message)
	{
		super(message);
	}

	/** @param cause причина */
	public DocumentLogicException(Throwable cause)
	{
		super(cause.getMessage(), cause);
	}

	public DocumentLogicException(Throwable cause, String errCode)
	{
		super(cause.getMessage(), cause);
		this.errCode = errCode;
	}

	/**
	 * @param message сообщение
	 * @param cause   причина
	 */
	public DocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}