package com.rssl.phizic.business;

/**
 * @author akrenev
 * @ created 16.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Исключение при срабоатывании рестрикшена
 */

public class RestrictionException extends BusinessLogicException
{
	/**
	 * конструктор
	 * @param message сообщение
	 */
	public RestrictionException(String message)
	{
		super(message);
	}
}
