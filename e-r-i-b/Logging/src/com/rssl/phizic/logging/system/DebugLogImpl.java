package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.debug.DebugRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 *
 * Лог только для отладки, не учитывает доступности по уровням логирования. Пишет в системлог.
 *
 * @ author: Gololobov
 * @ created: 08.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DebugLogImpl extends LogImpl
{
	public DebugLogImpl(LogModule module)
	{
		super(module);
	}

	private void rateLoging(int logLevel, Object message, Throwable t)
	{
		DebugRefreshConfig debugRefreshConfig = ConfigFactory.getConfig(DebugRefreshConfig.class);

		if (!debugRefreshConfig.isNeedRateLoging())
			return;

		//Если включено логирование изменения курсов валют
		StringBuilder builderMsg = new StringBuilder();
		builderMsg.append(message);

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement st : stackTrace)
		{
			builderMsg.append('\n'+st.toString());
		}

		log(logLevel, builderMsg, t);
	}

	public void trace(Object message)
	{
		rateLoging(LOG_LEVEL_TRACE, message, null);
	}

	public void debug(Object message)
	{
		rateLoging(LOG_LEVEL_DEBUG, message, null);
	}

	public void info(Object message)
	{
		rateLoging(LOG_LEVEL_INFO, message, null);
	}

	public void warn(Object message)
	{
		rateLoging(LOG_LEVEL_WARN, message, null);
	}

	public void error(Object message)
	{
		rateLoging(LOG_LEVEL_ERROR, message, null);
	}

	public void fatal(Object message)
	{
		rateLoging(LOG_LEVEL_FATAL, message, null);
	}
}
