package com.rssl.phizic.gate.bankroll;

/**
 * @ author: filimonova
 * @ created: 16.08.2010
 * @ $Author$
 * @ $Revision$
 */
public enum AccountState
{
	/**
     * ������
     */
	OPENED(0),

	/**
     * ������
     */
	CLOSED(1),

	/**
     * ���������
     */
	ARRESTED(2),

	/**
     * �������� ������. ������������
     */
	LOST_PASSBOOK(3);

	private int id;
	private String description;

	AccountState(int id)
	{
		this.id = id;

		switch (id)
		{
			case 0:
				description = "������";
			break;
			case 1:
				description = "������";
			break;
			case 2:
				description = "�� ���� ������� �����";
			break;
			case 3:
				description = "������� ����������";
			break;
		}
	}

	public int getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}

	public static AccountState valueOf(int id)
	{
		switch (id)
		{
			case 0:
				return OPENED;
			case 1:
				return CLOSED;
			case 2:
				return ARRESTED;
			case 3:
				return LOST_PASSBOOK;
		}
		throw new IllegalArgumentException("����������� ������ ����� [" + id + "]");
	}

}
