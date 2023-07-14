package com.rssl.phizic.business.skins;

/**
 * User: Balovtsev
 * Date: 20.05.2011
 * Time: 13:49:56
 *
 * ��������� � ������� ��������� ����
 */
public enum Category
{
	NONE,   // �������������� (�������) ����
	ADMIN,  // ��� ����������
	CLIENT, // ����������
	BOTH;   // ��� ������������� ����

	/**
	 * @return true, ���� ���������� ���������
	 */
	public boolean isClient()
	{
		return this == BOTH || this == CLIENT;
	}

	/**
	 * @return true, ���� ��������� ����������
	 */
	public boolean isAdmin()
	{
		return this == BOTH || this == ADMIN;
	}
}
