package com.rssl.phizicgate.mdm.common;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� �������
 */

public class ServiceInfo
{
	private String type;
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * @return ���
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * ������ ���
	 * @param type ���
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return ���� ������ ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ������ ���� ������ ��������
	 * @param startDate ����
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ���� ��������� ��������
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * ������ ���� ��������� ��������
	 * @param endDate ����
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
