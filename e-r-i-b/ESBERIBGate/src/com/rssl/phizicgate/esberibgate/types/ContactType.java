package com.rssl.phizicgate.esberibgate.types;

/**
 * @author egorova
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum ContactType
{
	HOME_PHONE("домашний телефон"),
	JOB_PHONE("рабочий телефон"),
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

	public static ContactType fromDescription(String description)
	{
		for(ContactType contactType : ContactType.values())
		{
			if (description.equals(contactType.description))
				return contactType;
		}
		throw new IllegalArgumentException("Неизвестный тип контакта \"" + description + "\"");
	}
}
