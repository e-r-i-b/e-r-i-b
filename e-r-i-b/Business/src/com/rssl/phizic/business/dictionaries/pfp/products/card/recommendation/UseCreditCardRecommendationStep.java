package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��� � �������������
 */

public class UseCreditCardRecommendationStep
{
	private String name;
	private String description;

	/**
	 * �����������
	 */
	public UseCreditCardRecommendationStep(){}

	/**
	 * @return ��� ����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��� ����
	 * @param name ��� ����
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ������ ��������
	 * @param description ��������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
