package com.rssl.phizic.business.pfp.portfolio.product;

/**
 * @author akrenev
 * @ created 30.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Инвестиционный продукт
 */

public class InvestmentBaseProduct extends BaseProduct
{
	private static final String RISK_FIELD_NAME = "riskName";
	private static final String INVESTMENT_PERIOD_FIELD_NAME = "investmentPeriod";

	/**
	 * задать название риска
	 * @param riskName название риска
	 */
	public void setRiskName(String riskName)
	{
		addExtendedField(RISK_FIELD_NAME, riskName);
	}

	/**
	 * @return название риска
	 */
	public String getRiskName()
	{
		return getExtendedFieldStringValue(RISK_FIELD_NAME);
	}

	/**
	 * задать значение горизонта инвестирования
	 * @param investmentPeriod значение горизонта инвестирования
	 */
	public void setInvestmentPeriod(String investmentPeriod)
	{
		addExtendedField(INVESTMENT_PERIOD_FIELD_NAME, investmentPeriod);
	}

	/**
	 * @return значение горизонта инвестирования
	 */
	public String getInvestmentPeriod()
	{
		return getExtendedFieldStringValue(INVESTMENT_PERIOD_FIELD_NAME);
	}
}
