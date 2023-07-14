package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ����������� ���� - ��������� ������
 */
public enum ApplicantType
{
	/**
	 * �������� ������
	 */
	MAIN_DEBITOR("MainDebitor")

	;

	private final String code;

	private ApplicantType(String code)
	{
		this.code = code;
	}

	/**
	 * @return �������� � ��������� Transact SM (never null)
	 */
	public String getCode()
	{
		return code;
	}
}
