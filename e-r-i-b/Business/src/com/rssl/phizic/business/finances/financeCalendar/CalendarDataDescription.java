package com.rssl.phizic.business.finances.financeCalendar;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author lepihina
 * @ created 23.04.14
 * $Author$
 * $Revision$
 * ƒанные дл€ построена€ финансового календар€
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
	 * ѕустой конструктор
	 */
	public CalendarDataDescription()
	{}

	/**
	 * @param dateType - тип даты
	 */
	public CalendarDataDescription(CalendarDateType dateType)
	{
		this.dateType = dateType;
	}

	/**
	 * @return дата в календаре
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - дата в календаре
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return тип даты
	 */
	public CalendarDateType getDateType()
	{
		return dateType;
	}

	/**
	 * @param dateType - тип даты
	 */
	public void setDateType(CalendarDateType dateType)
	{
		this.dateType = dateType;
	}

	/**
	 * @return сумма расходных операций
	 */
	public BigDecimal getOutcomeSum()
	{
		return outcomeSum;
	}

	/**
	 * @param outcomeSum - сумма расходных операций
	 */
	public void setOutcomeSum(BigDecimal outcomeSum)
	{
		this.outcomeSum = outcomeSum;
	}

	/**
	 * @return сумма доходных операций
	 */
	public BigDecimal getIncomeSum()
	{
		return incomeSum;
	}

	/**
	 * @param incomeSum - сумма доходных операций
	 */
	public void setIncomeSum(BigDecimal incomeSum)
	{
		this.incomeSum = incomeSum;
	}

	/**
	 * @return сумма, необходима€ дл€ оплаты автоплатежей и отложенных счетов
	 */
	public BigDecimal getPaymentsAmount()
	{
		return paymentsAmount;
	}

	/**
	 * @param paymentsAmount - сумма, необходима€ дл€ оплаты автоплатежей и отложенных счетов
	 */
	public void setPaymentsAmount(BigDecimal paymentsAmount)
	{
		this.paymentsAmount = paymentsAmount;
	}

	/**
	 * @return количество автоплатежей
	 */
	public int getAutoSubscriptionsCount()
	{
		return autoSubscriptionsCount;
	}

	/**
	 * @param autoSubscriptionsCount - количество автоплатежей
	 */
	public void setAutoSubscriptionsCount(int autoSubscriptionsCount)
	{
		this.autoSubscriptionsCount = autoSubscriptionsCount;
	}

	/**
	 * @return количество текущих/отложенных счетов
	 */
	public int getInvoiceSubscriptionsCount()
	{
		return invoiceSubscriptionsCount;
	}

	/**
	 * @param invoiceSubscriptionsCount - количество текущих/отложенных счетов
	 */
	public void setInvoiceSubscriptionsCount(int invoiceSubscriptionsCount)
	{
		this.invoiceSubscriptionsCount = invoiceSubscriptionsCount;
	}

	/**
	 * @return пуста€ ли €чейка
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
