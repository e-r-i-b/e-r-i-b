package com.rssl.phizic.messaging.ermb;

import com.rssl.phizic.common.types.UUID;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Контекст СМС-канала ЕРМБ
 */
public class ErmbSmsContext
{
	private static final ThreadLocal<UUID> incomingSmsRequestUID = new ThreadLocal<UUID>();
	private static final ThreadLocal<Calendar> currentSmsTime = new ThreadLocal<Calendar>();
	private static final ThreadLocal<Calendar> previousSmsTime = new ThreadLocal<Calendar>();

	/**
	 * @return идентификатор текущего входящего СМС-запроса (can be null)
	 */
	public static UUID getIncomingSMSRequestUID()
	{
		return incomingSmsRequestUID.get();
	}

	/**
	 * @param requestUID - идентификатор текущего входящего СМС-запроса (can be null)
	 */
	public static void setIncomingSMSRequestUID(UUID requestUID)
	{
		incomingSmsRequestUID.set(requestUID);
	}

	/**
	 * @return Время текущего смс запроса
	 */
	public static Calendar getCurrentSmsTime()
	{
		return currentSmsTime.get();
	}

	/**
	 * @param currentRequestSmsTime Время текущего смс запроса
	 */
	public static void setCurrentSmsTime(Calendar currentRequestSmsTime)
	{
		currentSmsTime.set(currentRequestSmsTime);
	}

	/**
	 * @return Время предыдущего смс запроса
	 */
	public static Calendar getPreviousSmsTime()
	{
		return previousSmsTime.get();
	}

	/**
	 * @param previousRequestSmsTime Время предыдущего смс запроса
	 */
	public static void setPreviousSmsTime(Calendar previousRequestSmsTime)
	{
		previousSmsTime.set(previousRequestSmsTime);
	}

	/**
	 * Сбросить значение текущего идентификатор запроса
	 */
	public static void clear()
	{
		incomingSmsRequestUID.remove();
		currentSmsTime.remove();
		previousSmsTime.remove();
	}
}
