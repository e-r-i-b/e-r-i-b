package com.rssl.phizic.business.mail;

/**
 * Способ получения ответа.
 * @author komarov
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */

public enum MailResponseMethod implements EnumWithDescription
{
	BY_PHONE("По телефону"),
	IN_WRITING("Письменно");

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
