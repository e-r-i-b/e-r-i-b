package com.rssl.phizic.business.mail;

/**
 * @author komarov
 * @ created 24.05.2011
 * @ $Author$
 * @ $Revision$
 */

public enum MailType implements EnumWithDescription
{
	CONSULTATION("Консультация"),
	COMPLAINT("Жалоба"),
	CLAIM("Претензия"),
	GRATITUDE("Благодарность"),
	IMPROVE("Предложение по улучшению услуги"),
	OTHER("Прочее");

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
