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
 * Выписка по автоплатежам для финансового календаря
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
	 * @return номер автоплатежа
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number номер автоплатежа
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return название автоплатежа
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description - название автоплатежа
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return наименование поставщика услуги
	 */
	public String getReceiverName()
	{
		return receiverName;
	}

	/**
	 * @param receiverName - наименование поставщика услуги
	 */
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * @return тип события исполнения регулярного платежа
	 */
	public ExecutionEventType getExecutionEventType()
	{
		return executionEventType;
	}

	/**
	 * @param executionEventType тип события исполнения регулярного платежа
	 */
	public void setExecutionEventType(ExecutionEventType executionEventType)
	{
		this.executionEventType = executionEventType;
	}

	/**
	 * @return тип суммы исполения регулярного платежа
	 */
	public SumType getSumType()
	{
		return sumType;
	}

	/**
	 * @param sumType тип суммы исполения регулярного платежа
	 */
	public void setSumType(SumType sumType)
	{
		this.sumType = sumType;
	}

	/**
	 * @return сумма списания по автоплатежу
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - сумма списания по автоплатежу
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return дата платежа
	 */
	public Calendar getPayDate()
	{
		return payDate;
	}

	/**
	 * @param payDate дата платежа
	 */
	public void setPayDate(Calendar payDate)
	{
		this.payDate = payDate;
	}
}
