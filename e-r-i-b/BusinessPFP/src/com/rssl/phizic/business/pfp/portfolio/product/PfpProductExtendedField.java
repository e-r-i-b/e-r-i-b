package com.rssl.phizic.business.pfp.portfolio.product;

/**
 * @author mihaylov
 * @ created 12.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������������� ���� � �������� �������� � �������� �������
 */
public class PfpProductExtendedField
{
	private Long id;
	private String key; //���� ����
	private String value; //�������� ����

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
