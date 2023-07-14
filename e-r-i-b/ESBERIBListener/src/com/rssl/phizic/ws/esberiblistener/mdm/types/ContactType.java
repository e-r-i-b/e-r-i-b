package com.rssl.phizic.ws.esberiblistener.mdm.types;

/**
 * @author egorova
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum ContactType
{
	HOME_PHONE("домашний телефон"),
	WORK_PHONE("рабочий телефон"),
	MOBILE_PHONE("мобильный телефон"),
	EMAIL("персональный email"),
	WORK_EMAIL("рабочий email"),
	FAX("факс");

    private String description;

	ContactType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

}
