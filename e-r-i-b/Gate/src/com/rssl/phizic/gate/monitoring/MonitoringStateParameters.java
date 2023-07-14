package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������� ��������� ��������
 */

public class MonitoringStateParameters
{
	private MonitoringGateState monitoringState;    //������, � ��������� ��������� ���������
	private Integer timeout;                        //�������� ��������
	private Boolean useMonitoring;                  //������������ �� ����������

	public MonitoringGateState getMonitoringState()
	{
		return monitoringState;
	}

	public void setMonitoringState(MonitoringGateState monitoringState)
	{
		this.monitoringState = monitoringState;
	}

	public void setMonitoringState(String monitoringState)
	{
		if (StringHelper.isNotEmpty(monitoringState))
			this.monitoringState = MonitoringGateState.valueOf(monitoringState);
	}

	public Integer getTimeout()
	{
		return timeout;
	}

	public void setTimeout(Integer timeout)
	{
		this.timeout = timeout;
	}

	public Boolean getUseMonitoring()
	{
		return useMonitoring;
	}

	public void setUseMonitoring(Boolean useMonitoring)
	{
		this.useMonitoring = useMonitoring;
	}
}
