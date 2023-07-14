package com.rssl.phizic.test.externalSystem.monitoring.result;

import com.rssl.phizic.logging.messaging.System;

import java.util.List;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� �������
 */

public class MonitoringESSystemInfo
{
	private final System system;
	private final List<MonitoringESRequestInfo> requests;
	private final long count;
	private long[] countByApplication;

	/**
	 * �����������
	 * @param system �������
	 * @param requests ���������� � ������� ��������
	 * @param count ����� ���������� ��������
	 * @param countByApplication ���������� �������� � ������� ������� ������������ ������
	 */
	public MonitoringESSystemInfo(System system, List<MonitoringESRequestInfo> requests, long count, long[] countByApplication)
	{
		this.system = system;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.requests = requests;
		this.count = count;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.countByApplication = countByApplication;
	}

	/**
	 * @return �������
	 */
	public System getSystem()
	{
		return system;
	}

	/**
	 * @return ���������� � ������� ��������
	 */
	public List<MonitoringESRequestInfo> getRequests()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return requests;
	}

	/**
	 * @return ����� ���������� ��������
	 */
	public long getCount()
	{
		return count;
	}

	/**
	 * @return ���������� �������� � ������� ������� ������������ ������
	 */
	public long[] getCountByApplication()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return countByApplication;
	}
}
