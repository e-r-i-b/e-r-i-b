package com.rssl.phizic.operations.finances;

import java.util.Calendar;

/**
 * @author gololobov
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */
/*
 * ��������� ������� ������� "�������� ������� �� ���������" � ������� ������ ��������
 */
public class CategoriesDynamicMoveDescription
{
	//���������� ����� �������
	private Long id;
	//������ ���� �������
	private Calendar firstDay;
	//��������� ���� �������
	private Calendar lastDay;
	//����� �� ���� ��������� �� ������
	private Double nationalSumm;
	//�������� �����
	private Double totalSumm;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getFirstDay()
	{
		return firstDay;
	}

	public void setFirstDay(Calendar firstDay)
	{
		this.firstDay = firstDay;
	}

	public Calendar getLastDay()
	{
		return lastDay;
	}

	public void setLastDay(Calendar lastDay)
	{
		this.lastDay = lastDay;
	}

	public Double getNationalSumm()
	{
		return nationalSumm;
	}

	public void setNationalSumm(Double nationalSumm)
	{
		this.nationalSumm = nationalSumm;
	}

	public Double getTotalSumm()
	{
		return totalSumm;
	}

	public void setTotalSumm(Double totalSumm)
	{
		this.totalSumm = totalSumm;
	}
}
