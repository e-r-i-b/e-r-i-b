package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.messaging.MessagingLogEntry;

/**
 * @author eMakarov
 * @ created 16.06.2009
 * @ $Author$
 * @ $Revision$
 */
public interface MessageLogWriter
{
	/**
	 * Добавление записи в журнал сообщений
 	 * @param entry - объект, представляет собой запись в журнале сообщений
	 * @throws Exception
	 */
	void write(MessagingLogEntry entry) throws Exception;
}
