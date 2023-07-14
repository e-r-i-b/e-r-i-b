package com.rssl.phizic.web.configure.gate;

import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Форма списка сервисов IQWave
 */

public class ListMonitoringGateConfigForm extends ListFormBase<MonitoringServiceGateConfig>
{
	private String newState;

	/**
	 * @return новое состояние сервисов
	 */
	public String getNewState()
	{
		return newState;
	}

	/**
	 * задать новое состояние сервисов
	 * @param newState новое состояние сервисов
	 */
	@SuppressWarnings("UnusedDeclaration") // для struts
	public void setNewState(String newState)
	{
		this.newState = newState;
	}
}
