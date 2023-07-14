package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.writers.SystemLogWriter;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Erkin
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг систем-лога для заглушки
 */
class MockSystemLogConfig extends SystemLogConfig
{
	private final SystemLogWriter logWriter = new MockSystemLogWriter();

	public MockSystemLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	public int getModuleLogLevel(LogModule moduleName)
	{
		// Логируем всё подряд
		return LogImpl.LOG_LEVEL_TRACE;
	}

	public int getModuleExtendedLogLevel(LogModule moduleName)
	{
		return LogImpl.LOG_LEVEL_TRACE;
	}

	public SystemLogWriter getMainWriter()
	{
		return logWriter;
	}

	public SystemLogWriter getBackupWriter()
	{
		return logWriter;
	}

	public String getDbWriterInstanceName()
	{
		throw new UnsupportedOperationException();
	}

	public String getJMSQueueName()
	{
		throw new UnsupportedOperationException();
	}

	public String getJMSQueueFactoryName()
	{
		throw new UnsupportedOperationException();
	}

	public void doRefresh()
	{
	}
}
