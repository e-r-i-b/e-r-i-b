package com.rssl.phizic.logging.operations.context;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;

/** Контекст операции. Хранит operUID.
 * @author akrenev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public final class OperationContext
{
    private static ThreadLocal<String> currentOperUID = new ThreadLocal<String>()
		{
			protected String initialValue()
			{
				return "AA" + new RandomGUID().getStringValue().substring(2);
			}
		};

	private static ThreadLocal<String> currentOperationName = new ThreadLocal<String>();

	/**
	 * установить operUID
	 * @param operUID - operUID
	 */
	public static void setCurrentOperUID(String operUID)
	{
		if (StringHelper.isEmpty(operUID))
			return;
		currentOperUID.set(operUID);
	}

	/**
	 * @return текущий operUID
	 */
	public static String getCurrentOperUID()
	{
		return currentOperUID.get();
	}

	/**
	 * @return название текущей операции
	 */
	public static String getCurrentOperationName()
	{
		return currentOperationName.get();
	}

	/**
	 * Устанавливает название текущей операции
	 * @param operationName - название операции
	 */
	public static void setCurrentOperationName(String operationName)
	{
		currentOperationName.set(operationName);
	}

	/**
	 * очистка контекста
	 */
	public static void clear()
	{
		currentOperUID.remove();
		currentOperationName.remove();
	}
}
