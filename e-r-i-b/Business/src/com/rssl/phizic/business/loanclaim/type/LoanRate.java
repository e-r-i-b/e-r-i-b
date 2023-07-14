package com.rssl.phizic.business.loanclaim.type;

import java.math.BigDecimal;

/**
 * ѕроцентна€ ставка по кредиту
 * @author Rtischeva
 * @ created 07.03.14
 * @ $Author$
 * @ $Revision$
 */
public class LoanRate
{
	private BigDecimal minLoanRate; //минимальна€ процентна€ ставка
	private BigDecimal maxLoanRate; //максимальна€ процентна€ ставка

	public LoanRate(BigDecimal minLoanRate)
	{
		if (minLoanRate == null)
			throw new IllegalArgumentException("аргумент minLoanRate не может быть null");

		this.minLoanRate = minLoanRate;
		this.maxLoanRate = minLoanRate;
	}

	public LoanRate(BigDecimal minLoanRate, BigDecimal maxLoanRate)
	{
		if (minLoanRate == null || maxLoanRate == null)
			throw new IllegalArgumentException("аргументы minLoanRate и  maxLoanRate не могут быть null");

		this.minLoanRate = minLoanRate;
		this.maxLoanRate = maxLoanRate;
	}

	public BigDecimal getMinLoanRate()
	{
		return minLoanRate;
	}

	public BigDecimal getMaxLoanRate()
	{
		return maxLoanRate;
	}
}
