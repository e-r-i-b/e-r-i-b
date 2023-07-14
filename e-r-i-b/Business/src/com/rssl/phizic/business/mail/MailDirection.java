package com.rssl.phizic.business.mail;

/**
 * @author komarov
 * @ created 24.05.2011
 * @ $Author$
 * @ $Revision$
 */

public enum MailDirection implements EnumWithDescription
{
	CLIENT("Отправлено"),
	ADMIN("Получено");


	private String description;

	MailDirection(String description)
	{
	    this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
