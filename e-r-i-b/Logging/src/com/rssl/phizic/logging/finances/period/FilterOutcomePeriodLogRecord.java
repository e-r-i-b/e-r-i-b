package com.rssl.phizic.logging.finances.period;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ������������ ������� �������� ���������� ��������.
 * ������ ���������� �������.
 * @author Koptyaev
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */
public class FilterOutcomePeriodLogRecord implements Serializable
{
	private Long id;
	private Calendar date;
	private String terBank;
	private PeriodType periodType;
	private boolean isDefault = false;

	/**
	 * �������� ������������� ������
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������� ������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * �������� ���� ������������
	 * @return ����
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * ���������� ���� ������������
	 * @param date ����
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * �������� ����� ��������
	 * @return �������
	 */
	public String getTerBank()
	{
		return terBank;
	}

	/**
	 * ���������� ����� ��������
	 * @param terBank �������
	 */
	public void setTerBank(String terBank)
	{
		this.terBank = terBank;
	}

	/**
	 * �������� ��� ������� (�����, �� 10 ����, 10-20 ����,20-30 ����, 30-90 ����, 90-183 ���)
	 * @return ��� �������
	 */
	public PeriodType getPeriodType()
	{
		return periodType;
	}
	/**
	 * ���������� ��� ������� (�����, �� 10 ����, 10-20 ����,20-30 ����, 30-90 ����, 90-183 ���)
	 * @param periodType ��� �������
	 */
	public void setPeriodType(PeriodType periodType)
	{
		this.periodType = periodType;
	}

	/**
	 * ��������� �� ��� �������
	 * @return ���������
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * ���������� ��������� ��� �������
	 * @param aDefault ���������
	 */
	public void setIsDefault(boolean aDefault)
	{
		isDefault = aDefault;
	}
}
