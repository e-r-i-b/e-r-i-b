package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������� ������� ��� ������ � ����������� �����������
 */

public interface BackRefMonitoringGateConfigService extends Service
{
	/**
	 * ��������� �������� �����������
	 * @param service ��� �������
	 * @return ��������� �����������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public MonitoringServiceGateConfig getMonitoringGateConfig(String service) throws GateLogicException, GateException;

	/**
	 * ������ ������ ��� �������
	 * @param service ��� �������
	 * @param state ����� ������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void setState(String service, MonitoringGateState state) throws GateLogicException, GateException;
}
