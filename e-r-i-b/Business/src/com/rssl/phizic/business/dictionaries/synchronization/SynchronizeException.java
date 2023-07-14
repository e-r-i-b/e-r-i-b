package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Исключение синхронизации справочников
 */

public class SynchronizeException extends IKFLException
{
	/**
	 * конструктор
	 * @param message сообщение
	 */
	public SynchronizeException(String message)
	{
		super(message);
	}

	/**
	 * конструктор
	 * @param message сообщение
	 * @param cause   ошибка
	 */
	public SynchronizeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
