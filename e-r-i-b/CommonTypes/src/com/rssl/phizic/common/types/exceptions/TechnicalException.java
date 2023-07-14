package com.rssl.phizic.common.types.exceptions;

/**
 * Техническая ошибка.
 * Проблема ее вызвавшая решается повторным вызовом.
 * @author Puzikov
 * @ created 27.05.14
 * @ $Author$
 * @ $Revision$
 */

public class TechnicalException extends SystemException
{
	/**
	 * ctor
	 * @param message сообщение
	 */
	public TechnicalException(String message)
	{
		super(message);
	}
}
