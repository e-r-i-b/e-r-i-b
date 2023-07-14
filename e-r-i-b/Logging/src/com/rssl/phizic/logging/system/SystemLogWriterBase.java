package com.rssl.phizic.logging.system;

import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.guest.GuestSystemLogEntry;
import com.rssl.phizic.logging.writers.SystemLogWriter;

/**
 * @author Kidyaev
 * @ created 27.03.2006
 * @ $Author: osminin $
 * @ $Revision: 78140 $
 */
public abstract class SystemLogWriterBase implements SystemLogWriter
{
	public final void write(LogModule source, String message, LogLevel level) throws Exception
	{
		// предотвращаем логгирование действий врайтера (иначе бесконечная рекурсия)
		if (LogThreadContext.isSystemLogWriter())
		{
			return;
		}

		LogThreadContext.setSystemLogWriter();
		try
		{
			doWrite(createEntryBase(source, message, level));
		}
		finally
		{
			LogThreadContext.removeSystemLogWriter();
		}
	}

	protected abstract void doWrite(SystemLogEntry logEntry) throws Exception;

	protected SystemLogEntry createEntryBase(LogModule source, String message, LogLevel level)
	{
		if (LogThreadContext.isGuest())
		{
			return new GuestSystemLogEntry(message, level, source);
		}

		return new SystemLogEntry(message, level, source);
	}
}
