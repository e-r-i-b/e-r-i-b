package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.push.StatisticLogEntry;

/**
 * Writer дл€ записи логов дл€ статистике
 * @author basharin
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public interface StatisticLogWriter
{
	/**
	 * ƒобавление записи в журнал статистики
     * @param entry - объект, представл€ет собой запись в журнале статистики
	 * @throws Exception
	 */
	void write(StatisticLogEntry entry) throws Exception;
}
