package com.rssl.phizic.business.messaging.info;

/**
 * Подписка
 * @author gladishev
 * @ created 07.05.2014
 * @ $Author$
 * @ $Revision$
 */

public interface Subscription
{
	/**
	 * @return id логина владельца подписки
	 */
	Long getLoginId();

	/**
	 * @return тип оповещения
	 */
	UserNotificationType getNotificationType();

	/**
	 * @return канал оповещения
	 */
	NotificationChannel getChannel();
}
