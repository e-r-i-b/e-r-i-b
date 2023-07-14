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
 * ������ ��� Rate
 */
public class RateBuilder
{
	// ���� ��������
	private Calendar creationDate;

	// ���� �������
	private Calendar orderDate;

	// ����� �������
    private String orderNumber;

	// ���� ������ ��������
	private Calendar effDate;

	// ������, �� ������� ������������
	private Currency fromCurrency;

	// �����, �� ������� ������������
	private BigDecimal fromValue;

	// ������, � ������� ������������
	private Currency toCurrency;

	// �����, � ������� ������������
	private BigDecimal toValue;

	// �������������, � �������� �������� ����
	private Department department;

	// ��� �����
	private CurrencyRateType currencyRateType;

	// �������� ��������� ����� � ������
	private BigDecimal dynamicValue;

	//�������� ��������� ����� �����
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
