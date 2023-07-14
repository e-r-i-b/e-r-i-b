package com.rssl.phizic.business.mail;

/**
 * ������ ��������� ������.
 * @author komarov
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */

public enum MailResponseMethod implements EnumWithDescription
{
	BY_PHONE("�� ��������"),
	IN_WRITING("���������");

	private String description;

	MailResponseMethod(String description)
	{
	    this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
	
}
