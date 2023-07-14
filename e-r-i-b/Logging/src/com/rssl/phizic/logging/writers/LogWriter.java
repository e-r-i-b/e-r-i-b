package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.LogEntry;
import com.rssl.phizic.logging.LoggingException;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс писателей логов
 */
public interface LogWriter
{

	/**
	 * Записать запись в лог
	 * @param entry сущность для записи
	 * @throws LoggingException - ошибка записи в лог
	 */
	public void write(LogEntry entry) throws LoggingException;

}
