package com.rssl.phizic.gate.mobilebank;

/**
* @author Erkin
* @ created 10.11.2014
* @ $Author$
* @ $Revision$
*/

/**
 * ��� ���������� ��������� ������-��-�����������
 * ������ ���������� ����������� � ��� � ����� (�������� �� ����)
 */
public enum MBKRegistrationResultCode
{
	/**
	 * ������� ���������������� � ����
	 */
	SUCCESS(0),

	/**
	 * ������ ��� ����������� � ����, ���������������� � ���
	 */
	ERROR_REG(1),

	/**
	 * ������ ��� ����������� � ����, �� �������������� � ���
	 */
	ERROR_NOT_REG(2),
	;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ��� � ���
	 */
	public final int mbkValue;

	private MBKRegistrationResultCode(int mbkValue)
	{
		this.mbkValue = mbkValue;
	}

	@Override
	public String toString()
	{
		return name() + "(" + mbkValue + ")";
	}
}
