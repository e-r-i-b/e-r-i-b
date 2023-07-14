package com.rssl.phizic.gate.mobilebank;

/**
 * ��� ��������
 * @author Puzikov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum MigrationType
{
	LIST("S"),          //��������� ��������
	PILOT("V"),         //�������� �� ��������� ���
	ON_THE_FLY("F");    //�������� �� ���� ��� ���������� ���

	private String code;

	private MigrationType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
