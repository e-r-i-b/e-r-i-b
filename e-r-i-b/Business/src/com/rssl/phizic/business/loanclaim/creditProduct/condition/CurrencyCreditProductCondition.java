package com.rssl.phizic.business.loanclaim.creditProduct.condition;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.annotation.JsonExclusion;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Moshenko
 * @ created 13.01.2014
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ���������� ��������.
 */
public class CurrencyCreditProductCondition implements Entity
{
	@JsonExclusion
	private Long id;
	/**
	 * ������� ����������� �������.
	 */
	private boolean clientAvaliable;
	/**
	 * ���� ������ ��������.
	 */
	private Calendar startDate;
	/**
	 * ������
	 */
	private Currency currency;
	/**
	 * ����������� ����� ���������� ������.(����������� ���� percentUse == false)
	 */
	private Money minLimitAmount;
	/**
	 * ������������ ����� ���������� ������. (����������� ����  percentUse == false)
	 */
	private Money maxLimitAmount;
	/**
	 * ������������ ����� ���������� ������ � ���������. (����������� ����  percentUse == false)
	 */
	private BigDecimal maxLimitPercent;
	/**
	 * ������� ���� ��� ������������ ������� ������� ������������ �����.
	 */
	private boolean maxLimitInclude;
	/**
	 * ������� ���� ��� ������������ �� ����� � ������� �� ��������� �������.
	 */
	private boolean maxLimitPercentUse;
	/**
	 * ����������� ����������� ������.
	 */
	private BigDecimal minPercentRate;
	/**
	 * ������������ ����������� ������.
	 */
	private BigDecimal maxPercentRate;
	/**
	 * ����� ������� �� ���������� ��������.
	 */
	@JsonExclusion
	private CreditProductCondition creditProductCondition;
	/**
	 * ���� ��������� � ������� ������� ��������� ���������� ������.(�� ��������� true)
	 */
	private boolean maxPercentRateInclude;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isClientAvaliable()
	{
		return clientAvaliable;
	}

	public void setClientAvaliable(boolean clientAvaliable)
	{
		this.clientAvaliable = clientAvaliable;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Money getMinLimitAmount()
	{
		return minLimitAmount;
	}

	public void setMinLimitAmount(Money minLimitAmount)
	{
		this.minLimitAmount = minLimitAmount;
	}

	public Money getMaxLimitAmount()
	{
		return maxLimitAmount;
	}

	public void setMaxLimitAmount(Money maxLimitAmount)
	{
		this.maxLimitAmount = maxLimitAmount;
	}

	public BigDecimal getMaxLimitPercent()
	{
		return maxLimitPercent;
	}

	public void setMaxLimitPercent(BigDecimal maxLimitPercent)
	{
		this.maxLimitPercent = maxLimitPercent;
	}

	public boolean isMaxLimitInclude()
	{
		return maxLimitInclude;
	}

	public void setMaxLimitInclude(boolean maxLimitInclude)
	{
		this.maxLimitInclude = maxLimitInclude;
	}

	public boolean isMaxLimitPercentUse()
	{
		return maxLimitPercentUse;
	}

	public void setMaxLimitPercentUse(boolean maxLimitPercentUse)
	{
		this.maxLimitPercentUse = maxLimitPercentUse;
	}

	public boolean isPercentUse()
	{
		return maxLimitPercentUse;
	}

	public void setPercentUse(boolean percentUse)
	{
		this.maxLimitPercentUse = percentUse;
	}

	public boolean isMaxPercentRateInclude()
	{
		return maxPercentRateInclude;
	}

	public void setMaxPercentRateInclude(boolean maxPercentRateInclude)
	{
		this.maxPercentRateInclude = maxPercentRateInclude;
	}

	public BigDecimal getMinPercentRate()
	{
		return minPercentRate;
	}

	public void setMinPercentRate(BigDecimal minPercentRate)
	{
		this.minPercentRate = minPercentRate;
	}

	public BigDecimal getMaxPercentRate()
	{
		return maxPercentRate;
	}

	public void setMaxPercentRate(BigDecimal maxPercentRate)
	{
		this.maxPercentRate = maxPercentRate;
	}

	public CreditProductCondition getCreditProductCondition()
	{
		return creditProductCondition;
	}

	public void setCreditProductCondition(CreditProductCondition creditProductCondition)
	{
		this.creditProductCondition = creditProductCondition;
	}


}
