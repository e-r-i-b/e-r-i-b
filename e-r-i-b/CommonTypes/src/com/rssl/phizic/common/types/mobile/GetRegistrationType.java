package com.rssl.phizic.common.types.mobile;

/**
 * @author osminin
 * @ created 18.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * Тип хранимой процедуры
 */
public enum GetRegistrationType
{
	FIRST("mb_WWW_GetRegistrations"),
	SECOND("mb_WWW_GetRegistrations2"),
	THIRD("mb_WWW_GetRegistrations3"),
	FIRST_PACK("mb_WWW_GetRegistrationsPack"),
	THIRD_PACK("mb_WWW_GetRegistrations3Pack");

	private String description;

	private GetRegistrationType(String description)
	{
		this.description = description;
	}

	public String getDecription()
	{
		return description;
	}
}
