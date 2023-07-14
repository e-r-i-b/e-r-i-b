package com.rssl.phizic.monitoring.core.percent;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.IncrementMode;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.monitoring.core.MonitoringGateServiceConfiguration;

/**
 * �������� ���������� ���������
 * @author Jatsky
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringCounterIncrementerPercent
{
	/**
	 * ���������� ��������
	 * @param configuration ������������ ��������
	 * @param incrementMode ����� ���������� ��������, ���������� ����� �� ��������� ���������� ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public static void doInc(MonitoringGateServiceConfiguration configuration, IncrementMode incrementMode, boolean isFail) throws GateException
	{
		if(incrementMode.checkForState(MonitoringGateState.DEGRADATION))
			((MonitoringGateServiceStateConfigurationPercent)configuration.getDegradationConfigPercent()).getCounterPercent().inc(isFail);
		if(incrementMode.checkForState(MonitoringGateState.INACCESSIBLE))
			((MonitoringGateServiceStateConfigurationPercent)configuration.getInaccessibleConfigPercent()).getCounterPercent().inc(isFail);
	}
}
