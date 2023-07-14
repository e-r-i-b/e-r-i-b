package com.rssl.phizic.test.externalSystem.monitoring.result;

import com.rssl.phizic.common.types.Application;

import java.util.List;

/**
 * @author akrenev
 * @ created 25.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������ �����������
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class MonitoringESResult
{
	private final Application[] applications;
	private final List<MonitoringESSystemInfo> systemInformationList;

	/**
	 * �����������
	 * @param applications ����������, ������� ��������� ������ � ��
	 * @param systemInformationList ��������� ����������
	 */
	public MonitoringESResult(Application[] applications, List<MonitoringESSystemInfo> systemInformationList)
	{
		this.applications = applications;
		this.systemInformationList = systemInformationList;
	}

	/**
	 * @return ����������, ������� ��������� ������ � ��
	 */
	public Application[] getApplications()
	{
		return applications;
	}

	/**
	 * @return ��������� ����������
	 */
	public List<MonitoringESSystemInfo> getSystemInformationList()
	{
		return systemInformationList;
	}
}
