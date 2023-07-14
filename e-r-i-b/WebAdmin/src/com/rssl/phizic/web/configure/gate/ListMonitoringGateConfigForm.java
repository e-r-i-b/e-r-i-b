package com.rssl.phizic.web.configure.gate;

import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ �������� IQWave
 */

public class ListMonitoringGateConfigForm extends ListFormBase<MonitoringServiceGateConfig>
{
	private String newState;

	/**
	 * @return ����� ��������� ��������
	 */
	public String getNewState()
	{
		return newState;
	}

	/**
	 * ������ ����� ��������� ��������
	 * @param newState ����� ��������� ��������
	 */
	@SuppressWarnings("UnusedDeclaration") // ��� struts
	public void setNewState(String newState)
	{
		this.newState = newState;
	}
}
