package com.rssl.phizic.logging.operations;

/**
 * @author vagin
 * @ created 26.10.2012
 * @ $Author$
 * @ $Revision$
 * �������� � ��� ��� �������. � ������� ����� �������� ������ �� ��������, ������� ������� � ���� �����!
 */
public enum CSAOperations
{
	AuthenticationOperation("��������������"),
	CancelMobileRegistrationOperation("���������� (����������) ���������� ����������"),
	CheckSessionOperation("�������� ������ �� ����������"),
	CloseSessionOperation("�������� ������"),
	MobileAuthenticationOperation("�������������� ���������� ����������"),
	MobileRegistrationOperation("����������� ������ ���������� ����������"),
	RestorePasswordOperation("�������������� ������"),
	UserLogon("���� � �������"),
	UserRegistration("����������� ������ �������"),
	GeneratePasswordOperation("��������� ������ ������"),
	ChangePasswordOperation("����� ������"),
	CheckPasswordOperation("�������� ������");


	private String description;

	CSAOperations(String str)
	{
		description = str;
	}

	public String getDescription()
	{
		return description;	
	}

	public static String[] getStringValues()
	{
		CSAOperations[] values = values();
		String[] tmp = new String[values.length];
		for (int i = 0; i < values.length; i++)
			tmp[i] = values[i].name();
		return tmp;

	}
}
