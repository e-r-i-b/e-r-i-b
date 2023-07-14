package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� ��������
 */

public interface IncrementMonitoringCounterService extends Service
{
	/**
	 * ���������� ��������
	 * @param service ��� ������� ��� �����������
	 * @param incrementMode ����� ���������� ��������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void increment(String service, IncrementMode incrementMode) throws GateLogicException, GateException;

	/**
	 * ���������� ����������� ��������
	 * @param service ��� ������� ��� �����������
	 * @param incrementMode ����� ���������� ��������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void incrementPercent(String service, IncrementMode incrementMode, boolean isFail) throws GateLogicException, GateException;
}
