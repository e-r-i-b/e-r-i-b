package com.rssl.phizic.business.dictionaries.providers;

import java.math.BigDecimal;

/**
 * @author bogdanov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �� ����� ��������.
 */

public class SumRestrictions
{
	/**
	 * ����������� ����������� ����� ��������.
	 */
	private BigDecimal minimumSum;
	/**
	 * ������������ ����������� ����� ��������.
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
