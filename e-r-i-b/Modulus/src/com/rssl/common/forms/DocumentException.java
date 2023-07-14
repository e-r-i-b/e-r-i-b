package com.rssl.common.forms;

/**
 * Фатальная ошибка при работе с документом
 * @author Evgrafov
 * @ created 21.11.2006
 * @ $Author: erkin $
 * @ $Revision: 48660 $
 */
public class DocumentException extends Exception
{

	/**
	 * @param message сообщение
	 */
	public DocumentException(String message)
	{
		super(message);
	}

	/**
	 * @param cause причина
	 */
	public DocumentException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message сообщение
	 * @param cause причина
	 */
	public DocumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
