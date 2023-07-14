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
 * �������� ���-������ ����
 */
public class ErmbSmsContext
{
	private static final ThreadLocal<UUID> incomingSmsRequestUID = new ThreadLocal<UUID>();
	private static final ThreadLocal<Calendar> currentSmsTime = new ThreadLocal<Calendar>();
	private static final ThreadLocal<Calendar> previousSmsTime = new ThreadLocal<Calendar>();

	/**
	 * @return ������������� �������� ��������� ���-������� (can be null)
	 */
	public static UUID getIncomingSMSRequestUID()
	{
		return incomingSmsRequestUID.get();
	}

	/**
	 * @param requestUID - ������������� �������� ��������� ���-������� (can be null)
	 */
	public static void setIncomingSMSRequestUID(UUID requestUID)
	{
		incomingSmsRequestUID.set(requestUID);
	}

	/**
	 * @return ����� �������� ��� �������
	 */
	public static Calendar getCurrentSmsTime()
	{
		return currentSmsTime.get();
	}

	/**
	 * @param currentRequestSmsTime ����� �������� ��� �������
	 */
	public static void setCurrentSmsTime(Calendar currentRequestSmsTime)
	{
		currentSmsTime.set(currentRequestSmsTime);
	}

	/**
	 * @return ����� ����������� ��� �������
	 */
	public static Calendar getPreviousSmsTime()
	{
		return previousSmsTime.get();
	}

	/**
	 * @param previousRequestSmsTime ����� ����������� ��� �������
	 */
	public static void setPreviousSmsTime(Calendar previousRequestSmsTime)
	{
		previousSmsTime.set(previousRequestSmsTime);
	}

	/**
	 * �������� �������� �������� ������������� �������
	 */
	public static void clear()
	{
		incomingSmsRequestUID.remove();
		currentSmsTime.remove();
		previousSmsTime.remove();
	}
}
