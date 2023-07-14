package com.rssl.phizic.business.finances.financeCalendar;

import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author lepihina
 * @ created 07.05.14
 * $Author$
 * $Revision$
 * ������� �� ������������ ��� ����������� ���������
 */
public class CalendarDayExtractByAutoSubscriptionDescription
{
	private String number;
	private String description;
	private String receiverName;
	private ExecutionEventType executionEventType;
	private SumType sumType;
	private BigDecimal amount;
	private Calendar payDate;

	/**
	 * @return ����� �����������
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number ����� �����������
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return �������� �����������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - �������� �����������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ������������ ���������� ������
	 */
	public String getReceiverName()
	{
		return receiverName;
	}

	/**
	 * @param receiverName - ������������ ���������� ������
	 */
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * @return ��� ������� ���������� ����������� �������
	 */
	public ExecutionEventType getExecutionEventType()
	{
		return executionEventType;
	}

	/**
	 * @param executionEventType ��� ������� ���������� ����������� �������
	 */
	public void setExecutionEventType(ExecutionEventType executionEventType)
	{
		this.executionEventType = executionEventType;
	}

	/**
	 * @return ��� ����� ��������� ����������� �������
	 */
	public SumType getSumType()
	{
		return sumType;
	}

	/**
	 * @param sumType ��� ����� ��������� ����������� �������
	 */
	public void setSumType(SumType sumType)
	{
		this.sumType = sumType;
	}

	/**
	 * @return ����� �������� �� �����������
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - ����� �������� �� �����������
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return ���� �������
	 */
	public Calendar getPayDate()
	{
		return payDate;
	}

	/**
	 * @param payDate ���� �������
	 */
	public void setPayDate(Calendar payDate)
	{
		this.payDate = payDate;
	}
}
