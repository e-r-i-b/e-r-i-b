package com.rssl.phizic.business;

/**
 * Исключение для вывода сообщения пользователю в
 * оранжевую рамочку
 *
 * @author hudyakov
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class MessageBusinessLogicException extends BusinessLogicException
{
	/**
	 * ctor
	 * @param message - сообщение
	 */
	public MessageBusinessLogicException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause - причина
	 */
	public MessageBusinessLogicException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message - сообщение
	 * @param cause   - причина
	 */
	public MessageBusinessLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
