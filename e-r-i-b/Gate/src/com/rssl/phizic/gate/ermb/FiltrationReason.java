package com.rssl.phizic.gate.ermb;

/**
* @author Erkin
* @ created 10.11.2014
* @ $Author$
* @ $Revision$
*/

/**
 * �������� �������, �� ������� ��� ������� ������ � ����
 * ������ ���������� ����������� � ��� � ����� (�������� �� ����)
 */
@SuppressWarnings("PackageVisibleField")
public enum FiltrationReason
{
	ERMB_PHONE("������� � ����"),

	ERMB_CARD("����� � ����"),

	PILOT_ZONE("����� � ��"),       //�� ����� "�������� ����", �� ��� �������� � ����� ����

	;

	public final String mbkValue;

	private FiltrationReason(String mbkValue)
	{
		this.mbkValue = mbkValue;
	}

	public static FiltrationReason fromMBK(String mbkValue)
	{
		for (FiltrationReason reason : values())
		{
			if (reason.mbkValue.equals(mbkValue))
				return reason;
		}

		throw new IllegalArgumentException("����������� FiltrationReason");
	}

	@Override
	public String toString()
	{
		return mbkValue;
	}
}
