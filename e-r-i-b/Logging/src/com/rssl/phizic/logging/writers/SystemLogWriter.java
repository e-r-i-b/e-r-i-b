package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.LogLevel;
import com.rssl.phizic.logging.system.SystemLogEntry;

/**
 * @author eMakarov
 * @ created 16.06.2009
 * @ $Author$
 * @ $Revision$
 */
public interface SystemLogWriter
{
	/**
	 * записать сообщение
	 * @param source источник сообщени€ (шлюз, €дро, шедулер)
	 * @param message сообщение
	 * @param level уровень скоторым заимано сообщение(ошибка, предупреждение, информаци€...)
	 */
	void write(LogModule source, String message, LogLevel level) throws Exception;

	/**
	 * создать сущность
	 * @param source источник сообщени€ (шлюз, €дро, шедулер)
	 * @param message сообщение
	 * @param level уровень скоторым заимано сообщение(ошибка, предупреждение, информаци€...)
	 * @return запись системного журнала
	 */
	SystemLogEntry createEntry(LogModule source, String message, LogLevel level);
}
