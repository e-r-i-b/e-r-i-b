package com.rssl.phizic.business.mail;

/**
 * @author komarov
 * @ created 24.05.2011
 * @ $Author$
 * @ $Revision$
 */

public enum MailType implements EnumWithDescription
{
	CONSULTATION("������������"),
	COMPLAINT("������"),
	CLAIM("���������"),
	GRATITUDE("�������������"),
	IMPROVE("����������� �� ��������� ������"),
	OTHER("������");

	private String description;

	MailType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
