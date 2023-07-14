package com.rssl.phizic.business.dictionaries.providers;

import java.math.BigDecimal;

/**
 * @author bogdanov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ограничения на сумму операция.
 */

public class SumRestrictions
{
	/**
	 * Минимальная разрешенная сумма списания.
	 */
	private BigDecimal minimumSum;
	/**
	 * Максимальная разрешенная сумма списания.
	 */
	private BigDecimal maximumSum;

	public BigDecimal getMinimumSum()
	{
		return minimumSum;
	}

	public void setMinimumSum(BigDecimal minimumSum)
	{
		this.minimumSum = minimumSum;
	}

	public BigDecimal getMaximumSum()
	{
		return maximumSum;
	}

	public void setMaximumSum(BigDecimal maximumSum)
	{
		this.maximumSum = maximumSum;
	}
}
