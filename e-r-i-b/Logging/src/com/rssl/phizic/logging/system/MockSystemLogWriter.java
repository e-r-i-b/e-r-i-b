package com.rssl.phizic.logging.system;

import com.rssl.phizic.logging.writers.SystemLogWriter;

/**
 * @author Erkin
 * @ created 28.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Заглушечный писатель логов ничего не пишет
 * (вместо него всё отпишет в консоль апач, см. LogImpl)
 */
class MockSystemLogWriter implements SystemLogWriter
{
	public void write(LogModule source, String message, LogLevel level)
	{
	}

	public SystemLogEntry createEntry(LogModule source, String message, LogLevel level)
	{
		return null;
	}
}
