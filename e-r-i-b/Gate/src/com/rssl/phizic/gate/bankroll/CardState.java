package com.rssl.phizic.gate.bankroll;

/**
 * @author Omeliyanchuk
 * @ created 19.11.2007
 * @ $Author$
 * @ $Revision$
 */

public enum CardState
{
	/**
	 * ��������
	 */
	active("��������"),
	/**
	 * ��������
	 */
	closed("��������"),
	/**
	 * �������� ����������
	 */
	replenishment("�������� ����������"),
	/**
	 * �������� �������� � ��
	 */
	ordered("�������� �������� � ��"),
	/**
	 * �������� ������
	 */
	delivery("�������� ������"),
	/**
	 * �������������
	 */
	blocked("�������������"),
	/**
	 * ��������� ���������� ����� � ��
	 */
	changing("��������� ���������� ����� � ��"),
	/**
	 * �����������, ��� ���� �� ��������� ���� ��������,
	 * �.�. ���� � ����� ��������� ��������,
	 * ��� �������� �� ���������� ��������������� ������ ����, ������ ������������ ����.
	 */
	unknown("����������");

    private String description;

	CardState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
