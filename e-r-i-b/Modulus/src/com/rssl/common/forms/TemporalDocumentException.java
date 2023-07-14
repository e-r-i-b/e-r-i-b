package com.rssl.common.forms;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Временная ошибка при работе с документом
 */
public class TemporalDocumentException extends DocumentException
{
	/**
	 * @param message сообщение
	 */
	public TemporalDocumentException(String message)
	{
		super(message);
	}

	/**
	 * @param cause причина
	 */
	public TemporalDocumentException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message сообщение
	 * @param cause причина
	 */
	public TemporalDocumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
