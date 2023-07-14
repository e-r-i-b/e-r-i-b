package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� �������� �����������
 */

public interface UpdateMonitoringGateConfigService extends Service
{
	/**
	 * �������� ��������� �����������
	 * @param config ��������� �����������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void update(MonitoringServiceGateConfig config) throws GateLogicException, GateException;
}
