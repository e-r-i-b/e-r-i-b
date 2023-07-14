package com.rssl.phizic.business.creditcards.conditions;

import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.common.types.Currency;

/**
 * Доступный кредитный лимит и карточные кредитные продукты в разрезе валюты
 * @author Dorzhinov
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class IncomeCondition
{
	private Long id;
	private Long incomeId;                      //уровень дохода
	private Currency currency;                  //валюта
	private CardAmountStep minCreditLimit;      //мин. кредитный лимит
	private CardAmountStep maxCreditLimit;      //макс. кредитный лимит
	private Boolean isMaxCreditLimitInclude;    //"включительно" для макс. кредитного лимита

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getIncomeId()
	{
		return incomeId;
	}

	public void setIncomeId(Long incomeId)
	{
		this.incomeId = incomeId;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public CardAmountStep getMinCreditLimit()
	{
		return minCreditLimit;
	}

	public void setMinCreditLimit(CardAmountStep minCreditLimit)
	{
		this.minCreditLimit = minCreditLimit;
	}

	public CardAmountStep getMaxCreditLimit()
	{
		return maxCreditLimit;
	}

	public void setMaxCreditLimit(CardAmountStep maxCreditLimit)
	{
		this.maxCreditLimit = maxCreditLimit;
	}

	public Boolean isMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude;
	}

	public Boolean getMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude();
	}

	public void setMaxCreditLimitInclude(Boolean maxCreditLimitInclude)
	{
		isMaxCreditLimitInclude = maxCreditLimitInclude;
	}
}
