package com.rssl.phizic.business.mail;

/**
 * Enum �������� ������������ �� ����� ���������� �����.
 * @author komarov
 * @ created 24.05.2011
 * @ $Author$
 * @ $Revision$
 */

public enum RecipientMailState implements EnumWithDescription
{
	NEW("�����"),//�����.
	DRAFT("�������� ������"),//�������� ������.
	READ("���������"),//���������.
	ANSWER("����� ���������"),//����� ���������.
	
	NEW_EPLOYEE_MAIL("����� ������ ����������"),//����� ������ ����������.
	ANSWER_EPLOYEE_MAIL("����� ����������"),
	NONE("�������� ������ ������");//������������ ������.

	RecipientMailState(String description)
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
