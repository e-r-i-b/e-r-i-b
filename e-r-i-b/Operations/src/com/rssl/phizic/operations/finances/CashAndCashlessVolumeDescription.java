package com.rssl.phizic.operations.finances;

import java.util.Calendar;

/**
 * @author gololobov
 * @ created 14.09.2011
 * @ $Author$
 * @ $Revision$
 */

/*
 * ��������� ������� ������� "����� �������� � ����������� ��������" � ������� ������ ��������
 */
public class CashAndCashlessVolumeDescription
{
	//���������� ����� �������
	private Long id;
	//������ ���� �������
	private Calendar firstDay;
	//��������� ���� �������
	private Calendar lastDay;
	//����� �� �������� ������� � ������������ ������ �� ������
	private Double cashSumm;
	//����� �� ����������� ������� � ������������ ������ �� ������
	private Double cashlessSumm;
	//�������� ����� �� �������� �������
	private Double totalCashSumm;
	//�������� ����� �� ����������� �������
	private Double totalCashlessSumm;

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

	public Double getCashSumm()
	{
		return cashSumm;
	}

	public void setCashSumm(Double cashSumm)
	{
		this.cashSumm = cashSumm;
	}

	public Double getCashlessSumm()
	{
		return cashlessSumm;
	}

	public void setCashlessSumm(Double cashlessSumm)
	{
		this.cashlessSumm = cashlessSumm;
	}

	public Double getTotalCashSumm()
	{
		return totalCashSumm;
	}

	public void setTotalCashSumm(Double totalCashSumm)
	{
		this.totalCashSumm = totalCashSumm;
	}

	public Double getTotalCashlessSumm()
	{
		return totalCashlessSumm;
	}

	public void setTotalCashlessSumm(Double totalCashlessSumm)
	{
		this.totalCashlessSumm = totalCashlessSumm;
	}
}
