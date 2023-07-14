package com.rssl.phizic.gate.einvoicing;

/**
 * Статус уведомления.
 *
 * @author bogdanov
 * @ created 11.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum NotificationStatus
{
	/**
	 * Принят.
	 */
	CREATED,
	/**
	 * Уведомление выполнено.
	 */
	NOTIFIED,
	/**
	 * Ошибка уведомления.
	 */
	ERROR,
	/**
	 * Оповещение отменено.
	 */
	CANCELED
}
