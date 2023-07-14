package com.rssl.phizic.business.pfp.portfolio.product;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 12.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������� � �������� �������
 */
public class InsuranceBaseProduct extends BaseProduct implements LinkedBaseProduct
{
	private static final String SELECTED_PERIOD_ID_KEY = "selectedPeriodId";
	private static final String SELECTED_PERIOD_NAME_KEY = "selectedPeriodName";
	private static final String SELECTED_TERM_KEY = "selectedTerm";
	private static final String INSURANCE_COMPANY_KEY = "insuranceCompany";
	private static final String MONTH_IN_PERIOD_NAME_KEY = "monthInPeriod";
	private static final String INSURANCE_SUM_NAME_KEY = "insuranceSum";
	private static final String LINKED_PRODUCT_ID_KEY = "linkedProductId";

	/**
	 * ���������� ������������� ���������� �������
	 * @param selectedPeriodId
	 */
	public void setSelectedPeriodId(Long selectedPeriodId)
	{
		addExtendedField(SELECTED_PERIOD_ID_KEY,selectedPeriodId);
	}

	/**
	 * @return ������������� ���������� �������
	 */
	public Long getSelectedPeriodId()
	{
		return getExtendedFieldLongValue(SELECTED_PERIOD_ID_KEY);
	}

	/**
	 * ���������� �������� ���������� �������
	 * @param selectedPeriod - �������� ���������� �������
	 */
	public void setSelectedPeriodName(String selectedPeriod)
	{
		addExtendedField(SELECTED_PERIOD_NAME_KEY,selectedPeriod);
	}

	/**
	 * @return �������� �������� ���������� �������
	 */
	public String getSelectedPeriodName()
	{
		return getExtendedFieldStringValue(SELECTED_PERIOD_NAME_KEY);
	}

	/**
	 * ���������� ���� ��������� ���������
	 * @param selectedTerm
	 */
	public void setSelectedTermValue(Long selectedTerm)
	{
		addExtendedField(SELECTED_TERM_KEY,selectedTerm);
	}

	/**
	 * @return ���� ��������� ���������
	 */
	public Long getSelectedTermValue()
	{
		return getExtendedFieldLongValue(SELECTED_TERM_KEY);
	}

	/**
	 * ���������� �������� ��������� ��������
	 * @param insuranceCompany
	 */
	public void setInsuranceCompanyName(String insuranceCompany)
	{
		addExtendedField(INSURANCE_COMPANY_KEY,insuranceCompany);
	}

	/**
 	 * @return �������� ��������� ��������
	 */
	public String getInsuranceCompanyName()
	{
		return getExtendedFieldStringValue(INSURANCE_COMPANY_KEY);
	}

	/**
	 * ������������� ���������� ������� � ������� ��� ���������� ��������
	 * @param month - ���-�� �������
	 */
	public void setMonthInPeriod(Long month)
	{
		addExtendedField(MONTH_IN_PERIOD_NAME_KEY,month);
	}

	/**
	 * @return ���������� ������� � ��������� �������
	 */
	public Long getMonthInPeriod()
	{
		return getExtendedFieldLongValue(MONTH_IN_PERIOD_NAME_KEY);
	}

	/**
	 * ������������� ��������� ����� ��� ���������� ��������
	 * @param sum - ��������� �����
	 */                      
	public void setInsuranceSum(BigDecimal sum)
	{
		addExtendedField(INSURANCE_SUM_NAME_KEY, sum);
	}

	/**
	 * @return ��������� �����
	 */
	public BigDecimal getInsuranceSum()
	{
		return getExtendedFieldBigDecimalValue(INSURANCE_SUM_NAME_KEY);
	}

	public void setLinkedProductId(Long id)
	{
		addExtendedField(LINKED_PRODUCT_ID_KEY,id);
	}

	public Long getLinkedProductId()
	{
		return getExtendedFieldLongValue(LINKED_PRODUCT_ID_KEY);
	}
}
