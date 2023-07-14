package com.rssl.phizic.business.pfp.portfolio.product;

/**
 * @author akrenev
 * @ created 30.08.13
 * @ $Author$
 * @ $Revision$
 *
 * �������������� �������
 */

public class InvestmentBaseProduct extends BaseProduct
{
	private static final String RISK_FIELD_NAME = "riskName";
	private static final String INVESTMENT_PERIOD_FIELD_NAME = "investmentPeriod";

	/**
	 * ������ �������� �����
	 * @param riskName �������� �����
	 */
	public void setRiskName(String riskName)
	{
		addExtendedField(RISK_FIELD_NAME, riskName);
	}

	/**
	 * @return �������� �����
	 */
	public String getRiskName()
	{
		return getExtendedFieldStringValue(RISK_FIELD_NAME);
	}

	/**
	 * ������ �������� ��������� ��������������
	 * @param investmentPeriod �������� ��������� ��������������
	 */
	public void setInvestmentPeriod(String investmentPeriod)
	{
		addExtendedField(INVESTMENT_PERIOD_FIELD_NAME, investmentPeriod);
	}

	/**
	 * @return �������� ��������� ��������������
	 */
	public String getInvestmentPeriod()
	{
		return getExtendedFieldStringValue(INVESTMENT_PERIOD_FIELD_NAME);
	}
}
