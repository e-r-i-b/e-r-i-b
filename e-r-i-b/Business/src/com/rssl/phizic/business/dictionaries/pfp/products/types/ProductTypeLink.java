package com.rssl.phizic.business.dictionaries.pfp.products.types;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������, ����������� ������� ������� �����
 */

public class ProductTypeLink
{
	private String name;
	private String hint;

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���������
	 */
	public String getHint()
	{
		return hint;
	}

	/**
	 * ������ ���������
	 * @param hint ���������
	 */
	public void setHint(String hint)
	{
		this.hint = hint;
	}
}
