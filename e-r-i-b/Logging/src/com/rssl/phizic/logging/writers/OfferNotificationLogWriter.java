package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.offerNotification.NotificationLogEntry;

/**
 * Writer для записи логов для статистики по уведомлениям о предодобренных предложениях
 * @author lukina
 * @ created 31.07.2014
 * @ $Author$
 * @ $Revision$
 */
public interface OfferNotificationLogWriter
{

	/**
	 * записать сообщение
	 * @param entry сообщение
	 */
	void write(NotificationLogEntry entry) throws Exception;
}
