package com.rssl.phizic.business.connectUdbo;

/**
 * ������� ��� ������� �������� � ����������� ����
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public enum UDBOClaimRulesStatus
{
	/**
	 * ��������
	 */
	ACTIVE(1),

	/**
	 * ������
	 */
	ENTERED(2),

	/**
	 * ��������
	 */
	ARCHIVAL(3);

	UDBOClaimRulesStatus(int value)
	{
		this.value = value;
	}

	/**
	 * ��� �������
	 */
	private final int value;

	public int toValue()
	{
		return value;
	}

	public static UDBOClaimRulesStatus fromValue(int value)
	{
		if (value == ACTIVE.value)
			return ACTIVE;
		if (value == ENTERED.value)
			return ENTERED;
		if (value == ARCHIVAL.value)
			return ARCHIVAL;

		throw new IllegalArgumentException("����������� ������ ��� ������� ��������� � ����������� ���� [" + value + "]");
	}
}
