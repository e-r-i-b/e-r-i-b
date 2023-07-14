package com.rssl.common.forms;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Логическая ошибка при работе с документом (ошибка пользователя),
 * которая требует действий со стороны приложения
 */
public class RedirectDocumentLogicException extends DocumentLogicException
{

	/** @param message сообщение */
	public RedirectDocumentLogicException(String message)
	{
		super(message);
	}

	/** @param cause причина */
	public RedirectDocumentLogicException(Throwable cause)
	{
		super(cause == null ? null : cause.getMessage(), cause);
	}

	/**
	 * @param message сообщение
	 * @param cause   причина
	 */
	public RedirectDocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
