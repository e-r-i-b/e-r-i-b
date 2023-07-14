package com.rssl.phizic.business.promocodes;

import com.rssl.phizic.business.departments.Department;

import java.util.Calendar;

/**
 * ��������� �����-�����
 * @author gladishev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeSettings
{
	private Long id;
	private Department terbank;  //��
	private Calendar startDate; //���� ������ ���������� �����
	private Calendar endDate; //���� ��������� ���������� �����

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Department getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Department terbank)
	{
		this.terbank = terbank;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
