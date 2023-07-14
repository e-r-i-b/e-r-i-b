package com.rssl.phizic.common.types;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 28.03.2013
 * @ $Author$
 * @ $Revision$
 * �����, ����������� ������(�������� ���)
 */
public class Period
{
	private Calendar fromDate;
	private Calendar toDate;

	/**
	 * �����������. ��������� �����������. ��������� ���� ������ ���� �� ������ ��������
	 * @param fromDate ��������� ����
	 * @param toDate �������� ����
	 */
	public Period(Calendar fromDate, Calendar toDate)
	{
		if (fromDate == null)
		{
			throw new IllegalArgumentException("��������� ���� ������ ���� ������");
		}
		if (toDate == null)
		{
			throw new IllegalArgumentException("�������� ���� ������ ���� ������");
		}
		if (fromDate.after(toDate))
		{
			throw new IllegalArgumentException("�������� ���� ������ ���� ������ ���������");
		}
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	/**
	 * @return ��������� ���� �������
	 */
	public Calendar getFromDate()
	{
		return fromDate;
	}

	/**
	 * @return �������� ���� �������
	 */
	public Calendar getToDate()
	{
		return toDate;
	}
}
