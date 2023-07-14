package com.rssl.phizicgate.mdm.common;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� �����
 */

public class CardInfo
{
	private String number;
	private Calendar startDate;
	private Calendar expiredDate;

	/**
	 * @return �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ������ �����
	 * @param number �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return ���� ������ �������� ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ������ ���� ������ �������� ��������
	 * @param startDate ����
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ���� ���������� �������� ��������
	 */
	public Calendar getExpiredDate()
	{
		return expiredDate;
	}

	/**
	 * ������ ���� ���������� �������� ��������
	 * @param expiredDate ����
	 */
	public void setExpiredDate(Calendar expiredDate)
	{
		this.expiredDate = expiredDate;
	}
}
