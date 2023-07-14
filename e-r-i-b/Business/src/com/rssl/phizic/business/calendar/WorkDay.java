package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Calendar;

/**
 * @author Gainanov
 * @ created 24.03.2009
 * @ $Author $
 * @ $Revision $
 */
public class WorkDay implements Comparable
{
	private Calendar date;
	private boolean isWorkDay;
	private Long id;

	/**
	 * �������� ����
	 * @return date - ����
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * ���������� ����
	 * @param date ��������������� ����
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * ������� �������� ���
	 * @return ������� �������� ���
	 */
	public boolean getIsWorkDay()
	{
		return isWorkDay;
	}

	/**
	 * ���������� ������� �������� ���
	 * @param workDay ������� �������� ���
	 */
	public void setIsWorkDay(boolean workDay)
	{
		isWorkDay = workDay;
	}

	/**
	 * �������� �� ������
	 * @return �� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ��������� �� ������
	 * @param id �� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public int compareTo(Object o)
	{
		return date.compareTo( ((WorkDay)o).getDate());
	}

	public boolean equals(Object o)
	{
		return ((WorkDay)o).getDate().equals(date) && ((WorkDay)o).getIsWorkDay()==isWorkDay ;
	}

	public int hashCode()
	{
		return date.hashCode() + (isWorkDay ? 1 : 0);
	}
}
