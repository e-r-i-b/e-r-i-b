package com.rssl.phizic.test.externalSystem.monitoring.result;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ����������� ���� �������
 */

public class MonitoringESRequestInfo
{
	private final String messageType;
	private final long count;
	private final long avgTime;
	private final long[] countByApplication;

	/**
	 * �����������
	 * @param messageType ��� �������
	 * @param avgTime ������ ����� ����������
	 * @param count ���������� ��������
	 * @param countByApplication ���������� �������� � ������� ������� ������������ ������
	 */
	public MonitoringESRequestInfo(String messageType, long avgTime, long count, long[] countByApplication)
	{
		this.messageType = messageType;
		this.count = count;
		this.avgTime = avgTime;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.countByApplication = countByApplication;
	}

	/**
	 * @return ��� �������
	 */
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * @return ������� ����� ����������
	 */
	public long getAvgTime()
	{
		return avgTime;
	}

	/**
	 * @return ���������� ��������
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
