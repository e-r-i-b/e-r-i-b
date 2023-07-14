package com.rssl.phizic.business.operations.background;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class TaskResultBase  implements TaskResult
{
	private Calendar startDate;//���� ������ ��������� ������
	private Calendar endDate;//���� ��������� ��������� ������

	public Calendar getStartDate()
	{
		return startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * ���������� ���� ������ ��������� ������
	 * @param startDate ���� ������ ��������� ������
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * ���������� ���� ��������� ��������� ������
	 * @param endDate ���� ��������� ��������� ������
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
