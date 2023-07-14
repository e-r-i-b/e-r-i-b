package com.rssl.phizic.logging.logon;

/**
 * @author krenev
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 */
public enum LogonLogEntryType
{
	FIND("Поиск профиля"),
	LOGON("Вход");
	private String description;

	LogonLogEntryType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
