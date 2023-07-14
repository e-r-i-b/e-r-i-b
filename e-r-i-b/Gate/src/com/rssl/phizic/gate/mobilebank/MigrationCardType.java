package com.rssl.phizic.gate.mobilebank;

/**
 * ��� ����� � ��������� �������� ���
 * @author Puzikov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum MigrationCardType
{
	MAIN("M"),                  //�������� ������������ �������
	ADDITIONAL("MM"),           //��������������, ������� ����������� ������ �������� ��� ���� (��������� ����� � ����������� ������)
	ADDITIONAL_TO_OTHER("MO"),  //��������������, ������� ����������� ������ �������� ������� ������� (��������� ����� ������ ������)
	OTHERS_ADDITIONAL("OM");    //��������������, ������� ������ ������ �������� ������������ ������� (��������� ����� � ����������� ������)

	private String code;

	private MigrationCardType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public static MigrationCardType fromValue(String type)
	{
		for (MigrationCardType codeType : values())
			if (type.equals(codeType.getCode()))
				return codeType;

		throw new IllegalArgumentException("�����������  ��� ����� � ��������� �������� ���[" + type + "]");
	}
}
