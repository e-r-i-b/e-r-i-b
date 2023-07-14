package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.offerNotification.NotificationLogEntry;

/**
 * Writer ��� ������ ����� ��� ���������� �� ������������ � �������������� ������������
 * @author lukina
 * @ created 31.07.2014
 * @ $Author$
 * @ $Revision$
 */
public interface OfferNotificationLogWriter
{

	/**
	 * �������� ���������
	 * @param entry ���������
	 */
	void write(NotificationLogEntry entry) throws Exception;
}
