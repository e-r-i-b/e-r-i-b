package com.rssl.phizic.business.dictionaries.pfp.common;

import com.rssl.phizic.utils.DateHelper;

/**
 * @author mihaylov
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��� ��������, ��� ������������ ��� ����������� ���
 */

public enum PortfolioType
{
	START_CAPITAL("��������� �������", null),
	QUARTERLY_INVEST("�������������� ��������", (long) DateHelper.MONTH_IN_QUARTER)
	{
		public boolean isProductTypeAvailable(DictionaryProductType productType)
		{
			return productType != DictionaryProductType.INSURANCE
					&& productType != DictionaryProductType.COMPLEX_INSURANCE
					&& productType != DictionaryProductType.PENSION;
		}
	};

	private final String description;
	private final Long monthCount;

	private PortfolioType(String description, Long monthCount)
	{
		this.description = description;
		this.monthCount = monthCount;
	}

	/**
	 * @return �������� ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ��������� �������� �� ������ ������� � ��������
	 * @param productType - ��� ��������
	 * @return �������� �� ������ ������� � ��������
	 */
	public boolean isProductTypeAvailable(DictionaryProductType productType)
	{
		return true;
	}

	/**
	 * @return ���������� ������� ��������� � ���������
	 */
	public Long getMonthCount()
	{
		return monthCount;
	}
}
