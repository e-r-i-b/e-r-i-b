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
 * Повалютное условие по кредитному продукту.
 */
public class CurrencyCreditProductCondition implements Entity
{
	@JsonExclusion
	private Long id;
	/**
	 * Признак доступности клиенту.
	 */
	private boolean clientAvaliable;
	/**
	 * Дата начала действия.
	 */
	private Calendar startDate;
	/**
	 * Валюта
	 */
	private Currency currency;
	/**
	 * Минимальная сумма кредитного лимита.(Указывается если percentUse == false)
	 */
	private Money minLimitAmount;
	/**
	 * Максимальная сумма кредитного лимита. (Указывается если  percentUse == false)
	 */
	private Money maxLimitAmount;
	/**
	 * Максимальная сумма кредитного лимита в процентах. (Указывается если  percentUse == false)
	 */
	private BigDecimal maxLimitPercent;
	/**
	 * Признак того что включительно верхней границы максимальной суммы.
	 */
	private boolean maxLimitInclude;
	/**
	 * Признак того что используется не сумма а процент от стоимости кредита.
	 */
	private boolean maxLimitPercentUse;
	/**
	 * Минимальная процентрная ставка.
	 */
	private BigDecimal minPercentRate;
	/**
	 * Максимальная процентрная ставка.
	 */
	private BigDecimal maxPercentRate;
	/**
	 * Общие условие по кредитному продукту.
	 */
	@JsonExclusion
	private CreditProductCondition creditProductCondition;
	/**
	 * Поле относится к верхней границе диапазона процентная ставка.(по умолчанию true)
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
