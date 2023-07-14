package com.rssl.phizic.business.loans.conditions;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * Условие в разрезе валюты для Кредитного продукта
 * @author Dorzhinov
 * @ created 23.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoanCondition
{
	private Long id;
	private Long productId;                     //кредитный продукт
	private Currency currency;                  //валюта кредита
	private Money minAmount;                    //мин. сумма кредита
	private Money maxAmount;                    //макс. сумма кредита в валюте
	private Boolean isMaxAmountInclude;         //"включительно" для макс. суммы
	private BigDecimal maxAmountPercent;        //макс. сумма кредита в процентах от стоимости цели кредита
	private AmountType amountType;              //тип верхней границы суммы кредита: в валюте или в % от стоимости цели кредита
	private BigDecimal minInterestRate;         //мин. % ставка
	private BigDecimal maxInterestRate;         //макс. % ставка
	private Boolean isMaxInterestRateInclude;   //"включительно" для макс. % ставки

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Money getMinAmount()
	{
		return minAmount;
	}

	public void setMinAmount(Money minAmount)
	{
		this.minAmount = minAmount;
	}

	public Money getMaxAmount()
	{
		return maxAmount;
	}

	public void setMaxAmount(Money maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	public Boolean isMaxAmountInclude()
	{
		return isMaxAmountInclude;
	}

	public Boolean getMaxAmountInclude()
	{
		return isMaxAmountInclude();
	}

	public void setMaxAmountInclude(Boolean maxAmountInclude)
	{
		isMaxAmountInclude = maxAmountInclude;
	}

	public BigDecimal getMaxAmountPercent()
	{
		return maxAmountPercent;
	}

	public void setMaxAmountPercent(BigDecimal maxAmountPercent)
	{
		this.maxAmountPercent = maxAmountPercent;
	}

	public AmountType getAmountType()
	{
		return amountType;
	}

	public void setAmountType(AmountType amountType)
	{
		this.amountType = amountType;
	}

	public BigDecimal getMinInterestRate()
	{
		return minInterestRate;
	}

	public void setMinInterestRate(BigDecimal minInterestRate)
	{
		this.minInterestRate = minInterestRate;
	}

	public BigDecimal getMaxInterestRate()
	{
		return maxInterestRate;
	}

	public void setMaxInterestRate(BigDecimal maxInterestRate)
	{
		this.maxInterestRate = maxInterestRate;
	}

	public Boolean isMaxInterestRateInclude()
	{
		return isMaxInterestRateInclude;
	}

	public Boolean getMaxInterestRateInclude()
	{
		return isMaxInterestRateInclude();
	}

	public void setMaxInterestRateInclude(Boolean maxInterestRateInclude)
	{
		isMaxInterestRateInclude = maxInterestRateInclude;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof LoanCondition))
			return false;

		LoanCondition condition = (LoanCondition) o;

		if(!id.equals(condition.getId()))
			return false;
		if (!productId.equals(condition.productId))
			return false;
		if (!currency.getNumber().equals(condition.currency.getNumber()))
			return false;

		return true;
	}

	public int hashCode()
	{
		int result = productId.hashCode();
		result = 31 * result + currency.hashCode();
		return result;
	}
}
