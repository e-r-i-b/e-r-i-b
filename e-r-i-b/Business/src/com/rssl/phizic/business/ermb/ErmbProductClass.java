package com.rssl.phizic.business.ermb;


/**
 * User: moshenko
 * Date: 12.03.2013
 * Time: 16:01:01
 * ������ ��������� ����
 */
public enum ErmbProductClass

{
	preferential("��������"),
	premium("�������"),
	social("����������"),
	standart("��������");


	private String value;
	ErmbProductClass(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
