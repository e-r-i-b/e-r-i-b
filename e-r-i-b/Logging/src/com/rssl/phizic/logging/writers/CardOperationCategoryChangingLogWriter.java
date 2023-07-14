package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.finances.category.CardOperationCategoryChangingLogEntry;

/**
 * Writer для записи логов о изменении клиентом категорий
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface CardOperationCategoryChangingLogWriter
{
	/**
	 * записать сообщение
	 * @param logEntry сообщение
	 */
	void write(CardOperationCategoryChangingLogEntry logEntry) throws Exception;
}
