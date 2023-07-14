package com.rssl.phizic.authgate;

/**
 * �������, ������� ����� ���� ���������� ������������ WAY4 iPAS � CSA
 @author Pankin
 @ created 29.10.2010
 @ $Author$
 @ $Revision$
 */
public enum AuthStatusType
{
	//����� ����
	OK("�������� ���������� ��������"),

	AUTH_OK("�������� �����������"),

	ERR_PRMFMT("��������� ������� �������"),

	ERR_BADPSW("������������ ������"),

	ERR_USRSRV("�������� ��� ������������"),

	ERR_NOTRIES("��������� ����� ������� ����� ������������ ������"),

	ERR_NOPSW("��� ���������������� ������� � ������ ����������� �������"),

	ERR_FORMAT("������ �������"),

	ERR_TIMEOUT("��������� ���������� ����� ����� ������"),

	//---------------------------------------
	//���� ������ ��� WAY4 iPAS
	ERR_AGAIN("������������ ������, ��������� ���� ��� ���"),

	ERR_SID("������������ SID"),
	
	ERR_SCHEME("������������ SCHEME"),

	//---------------------------------------
	//���� ������ ��� CSA
	TIMEBLOCK("����������� ������ ������������"),

	ERR_ATTYPE("������������ ��� AuthToken"),

	ERR_ATEXP("AuthToken ���������"),

	ERR_MTYPE("����� �������������� ���������� (��� ����������)"),

	ERR_CANCEL("���������������� ������ ��������������");

	private String description;

	AuthStatusType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
