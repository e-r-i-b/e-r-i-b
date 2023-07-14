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
 * Курс валюты
 */
public class Rate extends DictionaryRecordBase
{
	private Long id;

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

	//Код тарифного плана
	private String tarifPlanCodeType;

	//Дата окончания срока действия курса.
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
    * Идентификатор
    * @return идентификатор
    */
	Long getId()
	{
		return id;
	}

	/**
	* Установить идентификатор
    * @param id идентификатор
    */
	void setId(Long id)
	{
		this.id = id;
	}

	/**
    * Дата создания
    * @return дата создания
    */
	Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	* Установить дату создания
    * @param creationDate дата создания
    */
	void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	* Дата приказа
	* @return дата приказа
	*/
	Calendar getOrderDate()
	{
		return orderDate;
	}

	/**
	* Установить дату приказа
    * @param orderDate дата приказа
    */
	void setOrderDate(Calendar orderDate)
	{
		this.orderDate = orderDate;
	}

	/**
	* Номер приказа
	* @return номер приказа
	*/
	String getOrderNumber()
	{
		return orderNumber;
	}

	/**
	* Установить номер приказа
    * @param orderNumber номер приказа
    */
	void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	/**
	* Дата начала действия
	* @return дата начала действия
	*/
	public Calendar getEffDate()
	{
		return effDate;
	}

	/**
	* Установить дату начала действия
    * @param effDate дата начала действия
    */
	void setEffDate(Calendar effDate)
	{
		this.effDate = effDate;
	}

	/**
	* Сумма, которую надо перевести
	* @return сумма,которую надо перевести
	*/
	public BigDecimal getFromValue()
	{
		return fromValue;
	}

	/**
	* Установить сумму, которую надо перевести
    * @param fromValue сумма, которую надо перевести
    */
	void setFromValue(BigDecimal fromValue)
	{
		this.fromValue = fromValue;
	}

	/**
	* Валюта из которой надо перевести
	* @return валюта из которой надо перевести
	*/
	public Currency getFromCurrency()
	{
		return fromCurrency;
	}

	/**
	* Установить валюту из которой надо перевести
    * @param fromCurrency валюта из которой надо перевести
    */
	void setFromCurrency(Currency fromCurrency)
	{
		this.fromCurrency = fromCurrency;
	}

	/**
	* Валюта, в которую надо перевести
	* @return валюта, в которую надо перевести
	*/
	public Currency getToCurrency()
	{
		return toCurrency;
	}

	/**
	* Установить валюту, в которую надо перевести
    * @param toCurrency валюта в которую надо перевести
    */
	void setToCurrency(Currency toCurrency)
	{
		this.toCurrency = toCurrency;
	}

	/**
	* Сумма, в которую надо перевести
	* @return сумма, в которую надо перевести
	*/
	public BigDecimal getToValue()
	{
		return toValue;
	}

	/**
	* Установить сумму, в которую надо перевести
    * @param toValue сумма, в которую надо перевести
    */
	void setToValue(BigDecimal toValue)
	{
		this.toValue = toValue;
	}

	/**
	* Департамент, для которого установлен курс
	* @return департамент
	*/
	Department getDepartment()
	{
		return department;
	}

	/**
	* Установить департамент
    * @param department департамент
    */
	void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	* Тип курса
	* @return тип курса
	*/
	CurrencyRateType getCurrencyRateType()
	{
		return currencyRateType;
	}

	/**
	* Установить тип курса
    * @param currencyRateType тип курса
    */
	void setCurrencyRateType(CurrencyRateType currencyRateType)
	{
		this.currencyRateType = currencyRateType;
	}

	/**
	 * Динамика изменения курса в валюте
	 * @return
	 */
	BigDecimal getDynamicValue()
	{
		return dynamicValue;
	}

	/**
	 * Установить динамику изменения курса в валюте
	 * @param dynamicValue
	 */
	void setDynamicValue(BigDecimal dynamicValue)
	{
		this.dynamicValue = dynamicValue;
	}

	/**
	 * Динамика изменения курса валюты
	 * @return DynamicExchangeRate
	 */
	public DynamicExchangeRate getDynamicExchangeRate()
	{
		return dynamicExchangeRate;
	}

	/**
	 * Установить динамику изменения курса валюты
	 * @param dynamicExchangeRate
	 */
	public void setDynamicExchangeRate(DynamicExchangeRate dynamicExchangeRate)
	{
		this.dynamicExchangeRate = dynamicExchangeRate;
	}

	/**
	 * Код тарифного плана
	 * @return
	 */
	public String getTarifPlanCodeType()
	{
		return tarifPlanCodeType;
	}

	/**
	 * Установить код тарифного плана
	 * @param tarifPlanCodeType
	 */
	public void setTarifPlanCodeType(String tarifPlanCodeType)
	{
		this.tarifPlanCodeType = tarifPlanCodeType;
	}

	/**
	 * @return дата выхода из срока действия.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @param expireDate дата выхода из срока действия.
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	/**
	* Привести курс к обратному.
	* (продажа евро за рубли, тоже самое что и покупка рублей за евро) 
    * @return инвертированный курс
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
		throw new IllegalArgumentException("Неизвестный тип курса" + type);
	}

	public Comparable getSynchKey()
	{
		return getEffDate().toString()+getFromCurrency().getCode()+getToCurrency().getCode()+getCurrencyRateType().getId();
	}
}
