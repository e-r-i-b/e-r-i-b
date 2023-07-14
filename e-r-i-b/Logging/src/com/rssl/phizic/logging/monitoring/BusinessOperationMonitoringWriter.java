package com.rssl.phizic.logging.monitoring;

import com.rssl.phizic.logging.LoggingException;

/**
 * Мониторинг бизнес операций
 *
 * @author bogdanov
 * @ created 24.02.15
 * @ $Author$
 * @ $Revision$
 */

public interface BusinessOperationMonitoringWriter
{
	/**
	 * Записывает информация для мониторинга в лог.
	 *
	 * @param logEntry запись лога.
	 * @throws LoggingException
	 */
	public void write(MonitoringEntry logEntry) throws LoggingException;
}
