package com.rssl.phizic.common.types.csa;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * ��� ������������� �����������
 */
public enum IdentificationType
{
	cardNumber("�����"),                //�� ������ �����
	login("�����"),                     //�� ������
	phoneNumber("�������"),             //�� ������ ��������
	sessionId("������������� ������"),  //�� ������������� ������
	authToken("�����"),                 //�� AuthToken
	OUID("guid ��������"),              //�� �������������� ��������
	UID("���������� �������������"),    //�� ����������� �������������� ���+���+��(���������� � ���� �������).
	connector("���������"),             //�� �������������� ����������
	guestLogin("�������� �����");       //�� ��������� ������


	private String description;

	IdentificationType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
