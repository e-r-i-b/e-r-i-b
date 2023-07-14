package com.rssl.phizic.logging.system;

import com.rssl.phizic.logging.LoggingAccessor;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Акцессор вычисления необходимости логирования системных сообщений
 *
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class SystemLoggingAccessor implements LoggingAccessor
{
	public boolean needNormalModeLogging(Object... parameters)
	{
		SystemLogConfig config = ConfigFactory.getConfig(SystemLogConfig.class);
		return (Integer)parameters[0] >= config.getModuleLogLevel((LogModule)parameters[1]);
	}

	public boolean needExtendedModeLogging(Object... parameters)
	{
		SystemLogConfig config = ConfigFactory.getConfig(SystemLogConfig.class);
		return (Integer)parameters[0] >= config.getModuleExtendedLogLevel((LogModule)parameters[1]);
	}
}
