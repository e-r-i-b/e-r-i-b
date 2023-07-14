package com.rssl.phizic.test.externalSystem.monitoring.result;

import com.rssl.phizic.common.types.Application;

import java.util.List;

/**
 * @author akrenev
 * @ created 25.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Результат работы мониторинга
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class MonitoringESResult
{
	private final Application[] applications;
	private final List<MonitoringESSystemInfo> systemInformationList;

	/**
	 * конструктор
	 * @param applications приложения, которые выполняли запрос к ВС
	 * @param systemInformationList собранная информация
	 */
	public MonitoringESResult(Application[] applications, List<MonitoringESSystemInfo> systemInformationList)
	{
		this.applications = applications;
		this.systemInformationList = systemInformationList;
	}

	/**
	 * @return приложения, которые выполняли запрос к ВС
	 */
	public Application[] getApplications()
	{
		return applications;
	}

	/**
	 * @return собранная информация
	 */
	public List<MonitoringESSystemInfo> getSystemInformationList()
	{
		return systemInformationList;
	}
}
