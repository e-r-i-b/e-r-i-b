package com.rssl.phizic.business.mail;

/**
 * @author komarov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */

public enum RecipientType implements EnumWithDescription
{
	PERSON("Пользователь"),
	ADMIN("Администратор");

	private String description;

	RecipientType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
