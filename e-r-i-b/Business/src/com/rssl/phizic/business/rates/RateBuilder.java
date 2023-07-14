package com.rssl.phizic.business.rates;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.common.types.DynamicExchangeRate;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Билдер для Rate
 */
public class RateBuilder
{
	// Дата создания
	private Calendar creationDate;

	// Дата приказа
	private Calendar orderDate;

	// Номер приказа
    private String orderNumber;

	// Дата начала действия
	private Calendar effDate;

	// Валюта, из которой конвертируют
	private Currency fromCurrency;

	// Сумма, из которой конвертируют
	private BigDecimal fromValue;

	// Валюта, в которую конвертируют
	private Currency toCurrency;

	// Сумма, в которую конвертируют
	private BigDecimal toValue;

	// Подразделение, к которому привязан курс
	private Department department;

	// Тип курса
	private CurrencyRateType currencyRateType;

	// Динамика изменения курса в валюте
	private BigDecimal dynamicValue;

	//Динамика изменения курса валют
	private DynamicExchangeRate dynamicExchangeRate;

	///////////////////////////////////////////////////////////////////////////

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public void setOrderDate(Calendar orderDate)
	{
		this.orderDate = orderDate;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public void setEffDate(Calendar effDate)
	{
		this.effDate = effDate;
	}

	public void setFromCurrency(Currency fromCurrency)
	{
		this.fromCurrency = fromCurrency;
	}

	public void setFromValue(BigDecimal fromValue)
	{
		this.fromValue = fromValue;
	}

	public void setToCurrency(Currency toCurrency)
	{
		this.toCurrency = toCurrency;
	}

	public void setToValue(BigDecimal toValue)
	{
		this.toValue = toValue;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public void setCurrencyRateType(CurrencyRateType currencyRateType)
	{
		this.currencyRateType = currencyRateType;
	}

	public void setDynamicValue(BigDecimal dynamicValue)
	{
		this.dynamicValue = dynamicValue;
	}

	public void setDynamicExchangeRate(DynamicExchangeRate dynamicExchangeRate)
	{
		this.dynamicExchangeRate = dynamicExchangeRate;
	}

	public Rate build()
	{
		return new Rate(department, creationDate, orderDate, orderNumber, effDate, fromCurrency, fromValue, toCurrency, toValue, currencyRateType, dynamicValue, dynamicExchangeRate);
	}
}
