package com.rssl.phizic.business.finances.financeCalendar;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author lepihina
 * @ created 23.04.14
 * $Author$
 * $Revision$
 * ������ ��� ���������� ����������� ���������
 */
public class CalendarDataDescription
{
	private Calendar date;
	private CalendarDateType dateType;
	private BigDecimal outcomeSum = BigDecimal.ZERO;
	private BigDecimal incomeSum = BigDecimal.ZERO;
	private BigDecimal paymentsAmount = BigDecimal.ZERO;
	private int autoSubscriptionsCount;
	private int invoiceSubscriptionsCount;

	/**
	 * ������ �����������
	 */
	public CalendarDataDescription()
	{}

	/**
	 * @param dateType - ��� ����
	 */
	public CalendarDataDescription(CalendarDateType dateType)
	{
		this.dateType = dateType;
	}

	/**
	 * @return ���� � ���������
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - ���� � ���������
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return ��� ����
	 */
	public CalendarDateType getDateType()
	{
		return dateType;
	}

	/**
	 * @param dateType - ��� ����
	 */
	public void setDateType(CalendarDateType dateType)
	{
		this.dateType = dateType;
	}

	/**
	 * @return ����� ��������� ��������
	 */
	public BigDecimal getOutcomeSum()
	{
		return outcomeSum;
	}

	/**
	 * @param outcomeSum - ����� ��������� ��������
	 */
	public void setOutcomeSum(BigDecimal outcomeSum)
	{
		this.outcomeSum = outcomeSum;
	}

	/**
	 * @return ����� �������� ��������
	 */
	public BigDecimal getIncomeSum()
	{
		return incomeSum;
	}

	/**
	 * @param incomeSum - ����� �������� ��������
	 */
	public void setIncomeSum(BigDecimal incomeSum)
	{
		this.incomeSum = incomeSum;
	}

	/**
	 * @return �����, ����������� ��� ������ ������������ � ���������� ������
	 */
	public BigDecimal getPaymentsAmount()
	{
		return paymentsAmount;
	}

	/**
	 * @param paymentsAmount - �����, ����������� ��� ������ ������������ � ���������� ������
	 */
	public void setPaymentsAmount(BigDecimal paymentsAmount)
	{
		this.paymentsAmount = paymentsAmount;
	}

	/**
	 * @return ���������� ������������
	 */
	public int getAutoSubscriptionsCount()
	{
		return autoSubscriptionsCount;
	}

	/**
	 * @param autoSubscriptionsCount - ���������� ������������
	 */
	public void setAutoSubscriptionsCount(int autoSubscriptionsCount)
	{
		this.autoSubscriptionsCount = autoSubscriptionsCount;
	}

	/**
	 * @return ���������� �������/���������� ������
	 */
	public int getInvoiceSubscriptionsCount()
	{
		return invoiceSubscriptionsCount;
	}

	/**
	 * @param invoiceSubscriptionsCount - ���������� �������/���������� ������
	 */
	public void setInvoiceSubscriptionsCount(int invoiceSubscriptionsCount)
	{
		this.invoiceSubscriptionsCount = invoiceSubscriptionsCount;
	}

	/**
	 * @return ������ �� ������
	 */
	public boolean getIsEmpty()
	{
		if (outcomeSum.compareTo(BigDecimal.ZERO) != 0)
			return false;

		if (incomeSum.compareTo(BigDecimal.ZERO) != 0)
			return false;

		if (paymentsAmount.compareTo(BigDecimal.ZERO) != 0)
			return false;

		if (autoSubscriptionsCount != 0)
			return false;

		if (invoiceSubscriptionsCount != 0)
			return false;

		return true;
	}
}
