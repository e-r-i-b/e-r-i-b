package com.rssl.phizic.monitoring.core;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.IncrementMode;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Менеджер счетчиков
 */

public class MonitoringCounterIncrementer
{

	/**
	 * увеличение счетчика
	 * @param configuration конфигурация счетчика
	 * @param incrementMode режим увеличения счетчика, показывает какой из счетчиков необходимо увеличить
	 * @throws GateException
	 */
	public static void doInc(MonitoringGateServiceConfiguration configuration, IncrementMode incrementMode) throws GateException
	{
		if(incrementMode.checkForState(MonitoringGateState.DEGRADATION))
			configuration.getDegradationConfig().getCounter().inc();
		if(incrementMode.checkForState(MonitoringGateState.INACCESSIBLE))
			configuration.getInaccessibleConfig().getCounter().inc();
	}
}
