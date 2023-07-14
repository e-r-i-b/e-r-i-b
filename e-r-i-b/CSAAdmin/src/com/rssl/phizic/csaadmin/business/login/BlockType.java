package com.rssl.phizic.csaadmin.business.login;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���� ����������
 */

public enum BlockType
{
	/**
	 * ������������� ��������
	 */
	system("������ � ������� ��������"),
	/**
	 * ������������� ����������� �� ��� ����������
	 */
	employee("������ � ������� �������� ���������������"),
	/**
	 * ������������� ��-�� �������� ���������� ������������� ����� ������
	 */
	wrongLogons("������ � ������� ��������"),
	/**
	 * ������������� ��-�� ���������� ������������
	 */
	longInactivity("������ � ������� ��������");

	private String prefix;

	BlockType(String prefix)
	{
		this.prefix = prefix;
	}

	public String getPrefix()
	{
		return prefix;
	}
}
