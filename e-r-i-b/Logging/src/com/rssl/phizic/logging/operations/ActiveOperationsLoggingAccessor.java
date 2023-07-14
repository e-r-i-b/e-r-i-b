package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.LoggingAccessor;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Акцессор вычисления необходимости логирования активных операций
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ActiveOperationsLoggingAccessor implements LoggingAccessor
{
	public boolean needNormalModeLogging(Object... parameters)
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
		//активные операции пишем всегда кроме выключенного лога.
		return config.getMode() != OperationsLogConfig.Mode.OFF;
	}

	public boolean needExtendedModeLogging(Object... parameters)
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
		//активные операции пишем всегда кроме выключенного лога.
		return config.getExtendedMode() != OperationsLogConfig.Mode.OFF;
	}
}
