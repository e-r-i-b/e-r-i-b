package com.rssl.phizic.business.messaging.info;

/**
 * ��������
 * @author gladishev
 * @ created 07.05.2014
 * @ $Author$
 * @ $Revision$
 */

public interface Subscription
{
	/**
	 * @return id ������ ��������� ��������
	 */
	Long getLoginId();

	/**
	 * @return ��� ����������
	 */
	UserNotificationType getNotificationType();

	/**
	 * @return ����� ����������
	 */
	NotificationChannel getChannel();
}
