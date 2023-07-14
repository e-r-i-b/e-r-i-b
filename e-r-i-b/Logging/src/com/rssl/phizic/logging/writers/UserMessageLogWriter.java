package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.settings.UserMessageLogRecord;

/**
 * Writer для логирования отправки оповещений клиенту
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface UserMessageLogWriter
{
	/**
	 * записать сообщение
	 * @param entry сообщение
	 */
	void write(UserMessageLogRecord entry) throws Exception;
}
