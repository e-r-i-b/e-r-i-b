package com.rssl.phizic.business.mail;

/**
 * Enum ��������� �������� ������
 * @author komarov
 * @ created 11.07.2011
 * @ $Author$
 * @ $Revision$
 */

public enum MailState implements EnumWithDescription
{
	NEW("����������"), // ������ �� �������� ����������
	CLIENT_DRAFT("��������"), //������ �������� ������ �������
	EMPLOYEE_DRAFT("��������"), // ������ �������� ������ ����������
	TEMPLATE("�����");          //����� ������, ��������� ���������, � ������� ������ �� ������ ���� ����� 

	MailState(String description)
	{
		this.description = description;
	}

	private String description;


	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
