package com.rssl.phizic.gate.mobilebank;

/**
 * ������� ���������� �������� �� ������ ����
 * @author Puzikov
 * @ created 13.04.15
 * @ $Author$
 * @ $Revision$
 */

public enum PhoneDisconnectionReason
{
	/**
	 * ����� ��������� ������ MSISDN
	 */
	CHANGE_MSISDN(1),

	/**
	 * �������� ������� �� ���������� ���
	 */
	OSS_DISCONNECT(2),

	/**
	 * ����������� ��������� �������� � ���
	 */
	ABONENT_DISCONNECT(3),

	/**
	 * ����� ��������� MSISDN c ���.���� �� ���.����
	 */
	PHIZ_TRANSFER_MSISDN(4),

	/**
	 * ����� ��������� MSISDN � ���. ���� �� ��.����
	 */
	JUR_TRANSFER_MSISDN(5),

	/**
	 * ������ (�����������)
	 */
	OTHER(-1),
	;

	public final int code;

	private PhoneDisconnectionReason(int code)
	{
		this.code = code;
	}

	/**
	 * @param reason ��� ������� (���)
	 * @return �������
	 */
	public static PhoneDisconnectionReason fromCode(int reason)
	{
		for (PhoneDisconnectionReason value : values())
		{
			if (value.code == reason)
				return value;
		}
		throw new IllegalArgumentException("����������� ��� �������: " + reason);
	}
}
