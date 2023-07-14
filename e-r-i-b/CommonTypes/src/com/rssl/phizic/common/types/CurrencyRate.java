package com.rssl.phizic.common.types;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * @author Kosyakov
 * @ created 17.10.2006
 * @ $Author: gololobov $
 * @ $Revision: 67450 $
 */

/**
 * fromCurrency - ������, �� ������� ������������
 * toCurrency - ������, � ������� ������������
 *
 * (�� ����, ���� ������� �� ��������� � �����, ��������, ������������ ���:
 *  fromCurrency = $
 *  fromValue = 1
 *  toCurrency = rub
 *  toValue = 23.55 (������� ���� ������� ��� ������� ������� ��� �����)
 *  type = ������� ��� ������� �������������� (� ����������� �� ����� toValue ����� �� ��� ���� ��������, �����������
 *  ��� ����������� ����� �� ���������� ����)
 * ������� = ���� �������� � ������� ������
 * ������� = ���� ������� ������
 */
public class CurrencyRate implements Serializable
{
	public static final int ROUNDING_SCALE = 4;

	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

	private Currency fromCurrency;
	private BigDecimal fromValue;
	private Currency toCurrency;
	private BigDecimal toValue;
	private CurrencyRateType type;
	private String tarifPlanCodeType;
	private Calendar expireDate;

	/**
	 * �������� ��������� ����� ������
	 */
	private DynamicExchangeRate dynamicExchangeRate = DynamicExchangeRate.NONE;

	public CurrencyRate ( Currency fromCurrency, BigDecimal fromValue,
	                      Currency toCurrency, BigDecimal toValue,
	                      CurrencyRateType type, String tarifPlanCodeType,
	                      Calendar expireDate)
	{
		assert fromCurrency!=null;
		assert fromValue!=null;
		assert toCurrency!=null;
		assert toValue!=null;
		assert type!=null;
		this.fromCurrency = fromCurrency;
		this.fromValue = fromValue;
		this.toCurrency = toCurrency;
		this.toValue = toValue;
		this.type = type;
		this.tarifPlanCodeType = tarifPlanCodeType;
		this.expireDate = expireDate;
	}

	public CurrencyRate ( Currency fromCurrency, BigDecimal fromValue,
	                      Currency toCurrency, BigDecimal toValue,
	                      CurrencyRateType type, String tarifPlanCodeType)
	{
		assert fromCurrency!=null;
		assert fromValue!=null;
		assert toCurrency!=null;
		assert toValue!=null;
		assert type!=null;
		this.fromCurrency = fromCurrency;
		this.fromValue = fromValue;
		this.toCurrency = toCurrency;
		this.toValue = toValue;
		this.type = type;
		this.tarifPlanCodeType = tarifPlanCodeType;
	}

	public CurrencyRate()
	{

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

	public void setType(CurrencyRateType type)
	{
		this.type = type;
	}

	public void setType(String type)
	{
		setType(CurrencyRateType.valueOf(type));
	}

	public void setTarifPlanCodeType(String tarifPlanCodeType)
	{
		this.tarifPlanCodeType = tarifPlanCodeType;
	}

	public Currency getFromCurrency ()
	{
		return fromCurrency;
	}

	public BigDecimal getFromValue ()
	{
		return fromValue;
	}

	public Currency getToCurrency ()
	{
		return toCurrency;
	}

	public BigDecimal getToValue ()
	{
		return toValue;
	}

	public CurrencyRateType getType ()
	{
		return type;
	}

	public String getTarifPlanCodeType()
	{
		return tarifPlanCodeType;
	}

	/**
	 * Note: ��� ��������� ����� �� ����, ������������ �� ���� �����, � com.rssl.phizic.utils.CurrencyUtils.convert()
	 * @return ��������� <toValue> / <fromValue>, ���������� �� <ROUNDING_SCALE> (4) ������
	 * (� ������� ���������� ������� ������)
	 */
	public BigDecimal getFactor()
	{
		return toValue.divide(fromValue, ROUNDING_SCALE, ROUNDING_MODE);
	}

	/**
	 * @return ��������� <toValue> / <fromValue>, ���������� �� ��������� � "scale" ������
	 * (� ������� ���������� ������� ������)
	 */
	public BigDecimal getFactor(int scale)
	{
		return toValue.divide(fromValue, scale, ROUNDING_MODE);
	}

	/**
	 * Note: ��� ������� ����� �� ����, ������������ �� ���� �����, � com.rssl.phizic.utils.CurrencyUtils.reverseConvert()
	 * @return ��������� <fromValue> / <toValue>, ���������� �� <ROUNDING_SCALE> (4) ������
	 * (� ������� ���������� ������� ������)
	 */
	public BigDecimal getReverseFactor()
	{
		return fromValue.divide(toValue, ROUNDING_SCALE, ROUNDING_MODE);
	}

	/**
	 * @param scale - �������� ����������
	 * @return ��������� <fromValue> / <toValue>, ���������� �� "scale" ������
	 */
	public BigDecimal getReverseFactor(int scale)
	{
		return fromValue.divide(toValue, scale, ROUNDING_MODE);
	}

	public boolean equals ( Currency from, Currency to, CurrencyRateType type )
	{
		return getFromCurrency().compare(from)&& getToCurrency().compare(to)&&getType().equals(type);
	}

	public DynamicExchangeRate getDynamicExchangeRate()
	{
		return dynamicExchangeRate;
	}

	public void setDynamicExchangeRate(DynamicExchangeRate dynamicExchangeRate)
	{
		this.dynamicExchangeRate = dynamicExchangeRate;
	}
	/**
	 * ������������ � JAXRPC, ��� ��� �� �� ����� �������� � ����� Enum
	 * @param dynamicExchangeRate
	 */
	public void setDynamicExchangeRate(String dynamicExchangeRate)
	{
		setDynamicExchangeRate(DynamicExchangeRate.valueOf(dynamicExchangeRate));
	}

	/**
	 * @return ���� ������ �� ����� �������� �����.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @param expireDate ���� ������ �� ����� �������� �����.
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public String toString()
	{
		String delimeter = "_";
		return StringUtils.join(new String[]{getFromCurrency().getCode(), delimeter, getToCurrency().getCode(),
				delimeter, getType().name(), delimeter, getTarifPlanCodeType()});
	}
}
