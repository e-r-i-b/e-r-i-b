package com.rssl.phizic.business.finances;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * Операция для построения графика на странице "операции" (доход расход)
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CardOperationAbstract
{
	private Long id; // Идентификатор операции
	private Calendar date; // Дата операции
	private BigDecimal operationSum; // Сумма операции в национальной валюте
	private boolean income; // Доходная ли операция

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public BigDecimal getOperationSum()
	{
		return operationSum;
	}

	public void setOperationSum(BigDecimal operationSum)
	{
		this.operationSum = operationSum;
	}

	public boolean getIncome()
	{
		return income;
	}

	public void setIncome(boolean income)
	{
		this.income = income;
	}
}
