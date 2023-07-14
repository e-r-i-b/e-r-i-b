package com.rssl.phizic.business.dictionaries.pfp.common;

/**
 * @author mihaylov
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��� �������� ��� �������� ������� ��������� �������� � �����������
 */

public enum DictionaryProductType
{
	INSURANCE("���������� �����������"),
	PENSION("���������� �������"),
	COMPLEX_INSURANCE("����������� ��������� �������"),
	COMPLEX_INVESTMENT("����������� �������������� �������"),
	COMPLEX_INVESTMENT_FUND("����������� �������������� �������: ������� + ���"),
	COMPLEX_INVESTMENT_FUND_IMA("����������� �������������� �������: ������� + ��� + ���"),
	ACCOUNT("�����"),
	FUND("������ �������������� ����"),
	IMA("������������ ������������� ����"),
	TRUST_MANAGING("������������� ����������");

	private final String description;

	private DictionaryProductType(String description)
	{
		this.description = description;
	}

	/**
	 * @return �������� ���� ��������
	 */
	public String getDescription()
	{
		return description;
	}
}
