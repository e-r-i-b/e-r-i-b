package com.rssl.phizic.gate.bankroll;

/**
 * @ author: Vagin
 * @ created: 15.08.13
 * @ $Author
 * @ $Revision
 * �������������� � �������� ���������
 */
public enum CardBonusSign
{
	A("�������� �����"),
	G("������� �����"),
	M("���"),
	Y("����������"),
	Z("������ �����"),
	O("����������� �����������������");

	private String description;

	CardBonusSign(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}


