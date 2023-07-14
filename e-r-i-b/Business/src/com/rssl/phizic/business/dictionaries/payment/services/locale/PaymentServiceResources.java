package com.rssl.phizic.business.dictionaries.payment.services.locale;

import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;

/**
 * @author mihaylov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������������� ������ ������
 */
public class PaymentServiceResources extends MultiBlockLanguageResources
{
	private String name;
	private String description;

	/**
	 * @return ������������ ������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ���������� ������������ ������
	 * @param name - ������������ ������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return �������� ������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ���������� �������� ������
	 * @param description �������� ������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
