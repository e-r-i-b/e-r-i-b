package com.rssl.phizic.business.pfp.portfolio.product;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 * ���������� ������� � �������� �������
 */
public class PensionBaseProduct extends BaseProduct implements LinkedBaseProduct
{
	private static final String SELECTED_PERIOD_KEY = "selectedPeriodKey";
	private static final String QURTERLY_INVEST_KEY = "quarterlyInvestKey";
	private static final String LINKED_PRODUCT_ID_KEY = "linkedProductId";
	private static final String PENSION_FUND_KEY = "pensionFund";

	/**
	 * ���������� ���� ��������
	 * @param period ����
	 */
	public void setSelectedPeriod(Long period)
	{
		addExtendedField(SELECTED_PERIOD_KEY,period);
	}

	/**
	 * @return ���� ��������
	 */
	public Long getSelectedPeriod()
	{
		return getExtendedFieldLongValue(SELECTED_PERIOD_KEY);
	}

	public void setQurterlyInvest(BigDecimal qurterlyInvest)
	{
		addExtendedField(QURTERLY_INVEST_KEY,qurterlyInvest);
	}

	public BigDecimal getQurterlyInvest()
	{
		return getExtendedFieldBigDecimalValue(QURTERLY_INVEST_KEY);
	}

	public void setLinkedProductId(Long id)
	{
		addExtendedField(LINKED_PRODUCT_ID_KEY,id);
	}

	public Long getLinkedProductId()
	{
		return getExtendedFieldLongValue(LINKED_PRODUCT_ID_KEY);
	}

	/**
	 * ������ �������� ����������� �����
	 * @param pensionFund �������� ����������� �����
	 */
	public void setPensionFund(String pensionFund)
	{
		addExtendedField(PENSION_FUND_KEY, pensionFund);
	}

	/**
	 * @return �������� ����������� �����
	 */
	public String getPensionFund()
	{
		return getExtendedFieldStringValue(PENSION_FUND_KEY);
	}
}
