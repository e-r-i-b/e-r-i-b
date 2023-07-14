package com.rssl.phizic.business.rates;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 30.09.2010
 * @ $Author$
 * @ $Revision$
 * ���� ������
 */
public class Rate extends DictionaryRecordBase
{
	private Long id;

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

	//��� ��������� �����
	private String tarifPlanCodeType;

	//���� ��������� ����� �������� �����.
	private Calendar expireDate;

	Rate(){}

	Rate(CurrencyRate rate)
	{
		this(null, null, null, null, null, rate.getFromCurrency(), rate.getFromValue(), rate.getToCurrency(), rate.getToValue(), rate.getType(), null, rate.getDynamicExchangeRate());
	}

	Rate(Currency from, Currency to, BigDecimal fromVal, BigDecimal toVal, CurrencyRateType rateType)
	{
		this(null, null, null, null, null, from, fromVal, to, toVal, rateType, null, null);
	}

	Rate(Department department, Calendar creationDate, Calendar orderDate, String orderNumber, Calendar effDate, Currency fromCurrency, BigDecimal fromValue, Currency toCurrency, BigDecimal toValue, CurrencyRateType currencyRateType, BigDecimal dynamicValue, DynamicExchangeRate dynamicExchangeRate)
	{
		this.id = null;
		this.department = department;
		this.creationDate = creationDate;
		this.orderDate = orderDate;
		this.orderNumber = orderNumber;
		this.effDate = effDate;
		this.fromCurrency = fromCurrency;
		this.fromValue = fromValue;
		this.toCurrency = toCurrency;
		this.toValue = toValue;
		this.currencyRateType = currencyRateType;
		this.dynamicValue = dynamicValue;
		this.dynamicExchangeRate = dynamicExchangeRate;
	}

	/**
    * �������������
    * @return �������������
    */
	Long getId()
	{
		return id;
	}

	/**
	* ���������� �������������
    * @param id �������������
    */
	void setId(Long id)
	{
		this.id = id;
	}

	/**
    * ���� ��������
    * @return ���� ��������
    */
	Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	* ���������� ���� ��������
    * @param creationDate ���� ��������
    */
	void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	* ���� �������
	* @return ���� �������
	*/
	Calendar getOrderDate()
	{
		return orderDate;
	}

	/**
	* ���������� ���� �������
    * @param orderDate ���� �������
    */
	void setOrderDate(Calendar orderDate)
	{
		this.orderDate = orderDate;
	}

	/**
	* ����� �������
	* @return ����� �������
	*/
	String getOrderNumber()
	{
		return orderNumber;
	}

	/**
	* ���������� ����� �������
    * @param orderNumber ����� �������
    */
	void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	/**
	* ���� ������ ��������
	* @return ���� ������ ��������
	*/
	public Calendar getEffDate()
	{
		return effDate;
	}

	/**
	* ���������� ���� ������ ��������
    * @param effDate ���� ������ ��������
    */
	void setEffDate(Calendar effDate)
	{
		this.effDate = effDate;
	}

	/**
	* �����, ������� ���� ���������
	* @return �����,������� ���� ���������
	*/
	public BigDecimal getFromValue()
	{
		return fromValue;
	}

	/**
	* ���������� �����, ������� ���� ���������
    * @param fromValue �����, ������� ���� ���������
    */
	void setFromValue(BigDecimal fromValue)
	{
		this.fromValue = fromValue;
	}

	/**
	* ������ �� ������� ���� ���������
	* @return ������ �� ������� ���� ���������
	*/
	public Currency getFromCurrency()
	{
		return fromCurrency;
	}

	/**
	* ���������� ������ �� ������� ���� ���������
    * @param fromCurrency ������ �� ������� ���� ���������
    */
	void setFromCurrency(Currency fromCurrency)
	{
		this.fromCurrency = fromCurrency;
	}

	/**
	* ������, � ������� ���� ���������
	* @return ������, � ������� ���� ���������
	*/
	public Currency getToCurrency()
	{
		return toCurrency;
	}

	/**
	* ���������� ������, � ������� ���� ���������
    * @param toCurrency ������ � ������� ���� ���������
    */
	void setToCurrency(Currency toCurrency)
	{
		this.toCurrency = toCurrency;
	}

	/**
	* �����, � ������� ���� ���������
	* @return �����, � ������� ���� ���������
	*/
	public BigDecimal getToValue()
	{
		return toValue;
	}

	/**
	* ���������� �����, � ������� ���� ���������
    * @param toValue �����, � ������� ���� ���������
    */
	void setToValue(BigDecimal toValue)
	{
		this.toValue = toValue;
	}

	/**
	* �����������, ��� �������� ���������� ����
	* @return �����������
	*/
	Department getDepartment()
	{
		return department;
	}

	/**
	* ���������� �����������
    * @param department �����������
    */
	void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	* ��� �����
	* @return ��� �����
	*/
	CurrencyRateType getCurrencyRateType()
	{
		return currencyRateType;
	}

	/**
	* ���������� ��� �����
    * @param currencyRateType ��� �����
    */
	void setCurrencyRateType(CurrencyRateType currencyRateType)
	{
		this.currencyRateType = currencyRateType;
	}

	/**
	 * �������� ��������� ����� � ������
	 * @return
	 */
	BigDecimal getDynamicValue()
	{
		return dynamicValue;
	}

	/**
	 * ���������� �������� ��������� ����� � ������
	 * @param dynamicValue
	 */
	void setDynamicValue(BigDecimal dynamicValue)
	{
		this.dynamicValue = dynamicValue;
	}

	/**
	 * �������� ��������� ����� ������
	 * @return DynamicExchangeRate
	 */
	public DynamicExchangeRate getDynamicExchangeRate()
	{
		return dynamicExchangeRate;
	}

	/**
	 * ���������� �������� ��������� ����� ������
	 * @param dynamicExchangeRate
	 */
	public void setDynamicExchangeRate(DynamicExchangeRate dynamicExchangeRate)
	{
		this.dynamicExchangeRate = dynamicExchangeRate;
	}

	/**
	 * ��� ��������� �����
	 * @return
	 */
	public String getTarifPlanCodeType()
	{
		return tarifPlanCodeType;
	}

	/**
	 * ���������� ��� ��������� �����
	 * @param tarifPlanCodeType
	 */
	public void setTarifPlanCodeType(String tarifPlanCodeType)
	{
		this.tarifPlanCodeType = tarifPlanCodeType;
	}

	/**
	 * @return ���� ������ �� ����� ��������.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @param expireDate ���� ������ �� ����� ��������.
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	/**
	* �������� ���� � ���������.
	* (������� ���� �� �����, ���� ����� ��� � ������� ������ �� ����) 
    * @return ��������������� ����
    */
	Rate inverse()
	{
		CurrencyRateType rateType = inverseCurrencyRateType(currencyRateType);
		return new Rate(department, creationDate, orderDate, orderNumber, effDate, toCurrency, toValue, fromCurrency, fromValue, rateType, null, dynamicExchangeRate);
	}

	public static CurrencyRateType inverseCurrencyRateType(CurrencyRateType type)
	{
		switch(type)
		{
			case CB:
				return CurrencyRateType.CB;
			case BUY:
				return CurrencyRateType.SALE;
			case SALE:
				return CurrencyRateType.BUY;
			case BUY_REMOTE:
				return CurrencyRateType.SALE_REMOTE;
			case SALE_REMOTE:
				return CurrencyRateType.BUY_REMOTE;
		}
		throw new IllegalArgumentException("����������� ��� �����" + type);
	}

	public Comparable getSynchKey()
	{
		return getEffDate().toString()+getFromCurrency().getCode()+getToCurrency().getCode()+getCurrencyRateType().getId();
	}
}
