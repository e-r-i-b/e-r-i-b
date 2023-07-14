package com.rssl.phizic.business.commission;

import java.math.BigDecimal;

/**
 * @author Evgrafov
 * @ created 11.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public class FixedRule extends CommissionRule
{
	private BigDecimal amount;

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}