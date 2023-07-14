package com.rssl.phizic.business.commission;

import java.math.BigDecimal;

/**
 * @author Evgrafov
 * @ created 11.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */
public class RateRule extends CommissionRule
{
	private BigDecimal rate;
	private BigDecimal minAmount;
	private BigDecimal maxAmount;

	public BigDecimal getMaxAmount()
	{
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	public BigDecimal getMinAmount()
	{
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount)
	{
		this.minAmount = minAmount;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}
}