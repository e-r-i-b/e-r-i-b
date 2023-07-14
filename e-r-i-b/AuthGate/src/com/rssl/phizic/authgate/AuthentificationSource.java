package com.rssl.phizic.authgate;

/**
 * @author egorova
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum AuthentificationSource
{
	//Полная версия
	full_version("осн. версия"),
	//Мобильная версия (в т.ч. iPhone)
	mobile_version("моб. версия"),
	//АТМ версия
	atm_version("атм версия");

	private String description;

	AuthentificationSource(String description)
	{
	   this.description = description;
	}

	/**
	 * @return Описание источника аутентификации
	 */
	public String getDescription()
	{
		return description;
	}
}
