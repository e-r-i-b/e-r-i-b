package com.rssl.common.forms;

/**
 * Исключение для передачи сообщения пользователю в
 * оранжевую рамочку приложения
 *
 * @author hudyakov
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class MessageDocumentLogicException extends DocumentLogicException
{
	/**
	 * ctor
	 * @param message - сообщение
	 */
	public MessageDocumentLogicException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause - причина
	 */
	public MessageDocumentLogicException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message - сообщение
	 * @param cause   - причина
	 */
	public MessageDocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
