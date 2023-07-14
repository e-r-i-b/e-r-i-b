package com.rssl.phizic.business.creditcards.products.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * �������� ��� �������� ������������� ���������
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardProductResources extends LanguageResource
{
	private String name;
	private String additionalTerms;

	/**
	 * @return �������� ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name �������� ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return �������������� �������
	 */
	public String getAdditionalTerms()
	{
		return additionalTerms;
	}

	/**
	 * @param additionalTerms �������������� �������
	 */

	public void setAdditionalTerms(String additionalTerms)
	{
		this.additionalTerms = additionalTerms;
	}
}
