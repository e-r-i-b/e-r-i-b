package com.rssl.phizic.business.dictionaries.providers;

/**
 * @author akrenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */

// ��� ����� ��� ������
public enum AccountType
{
	DEPOSIT(0),         // ���� ������
	CARD(1),            // ���� �����
	ALL(2);             // ���� ������ � ���� �����

	private int value;

	AccountType(int value)
	{
		this.value = value;
	}

	public static AccountType fromValue(int value)
	{
		if (DEPOSIT.value == value)
			return DEPOSIT;
		if (CARD.value == value)
			return CARD;
		if (ALL.value == value)
			return ALL;

		throw new IllegalArgumentException("����������� ��� ���� [" + value + "]");
	}
}
