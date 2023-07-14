package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.finances.period.FilterOutcomePeriodLogRecord;

/**
 * Writer для записи логов о выборе периода фильтрации расходов
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface FilterOutcomePeriodLogWriter
{
	/**
	 * записать сообщение
	 * @param logEntry сообщение
	 */
	void write(FilterOutcomePeriodLogRecord logEntry) throws Exception;
}
