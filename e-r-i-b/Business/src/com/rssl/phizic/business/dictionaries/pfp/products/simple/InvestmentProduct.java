package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;

/**
 * @author akrenev
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * класс инвестиционных продуктов
 */

public abstract class InvestmentProduct extends Product
{
	private Risk risk;
	private InvestmentPeriod investmentPeriod;

	/**
	 * @return риск
	 */
	public Risk getRisk()
	{
		return risk;
	}

	/**
	 * задать риск
	 * @param risk риск
	 */
	public void setRisk(Risk risk)
	{
		this.risk = risk;
	}

	/**
	 * @return "горизонт инвестирования"
	 */
	public InvestmentPeriod getInvestmentPeriod()
	{
		return investmentPeriod;
	}

	/**
	 * задать "горизонт инвестирования"
	 * @param investmentPeriod "горизонт инвестирования"
	 */
	public void setInvestmentPeriod(InvestmentPeriod investmentPeriod)
	{
		this.investmentPeriod = investmentPeriod;
	}
}
