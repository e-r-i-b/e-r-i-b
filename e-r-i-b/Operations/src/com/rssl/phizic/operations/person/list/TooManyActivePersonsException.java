package com.rssl.phizic.operations.person.list;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author mihaylov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Исключение выбрасываемое, когда нашлось больше профилей в ЕРИБе, чем ожидалось
 */
public class TooManyActivePersonsException extends BusinessLogicException
{
	/**
	 * Конструктор
	 * @param message сообщение
	 */
	public TooManyActivePersonsException(String message)
	{
		super(message);
	}
}
