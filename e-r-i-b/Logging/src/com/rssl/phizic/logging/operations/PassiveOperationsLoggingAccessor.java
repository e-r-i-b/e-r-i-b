package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.LoggingAccessor;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Акцессор вычисления необходимости логирования пассивных операций
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class PassiveOperationsLoggingAccessor implements LoggingAccessor
{
	public boolean needNormalModeLogging(Object... parameters)
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
		//пассивные операции пишем только в режиме "все операции".
		return config.getMode() == OperationsLogConfig.Mode.FULL;
	}

	public boolean needExtendedModeLogging(Object... parameters)
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
		//пассивные операции пишем только в режиме "все операции".
		return config.getExtendedMode() == OperationsLogConfig.Mode.FULL;
	}
}