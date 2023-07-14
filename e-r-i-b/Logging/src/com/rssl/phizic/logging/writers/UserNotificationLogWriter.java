package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.settings.UserNotificationLogRecord;

/**
 * Writer для логирования изменений настроек оповещений
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface UserNotificationLogWriter
{
	/**
	 * записать сообщение
	 * @param entry сообщение
	 */
	void write(UserNotificationLogRecord entry) throws Exception;
}

