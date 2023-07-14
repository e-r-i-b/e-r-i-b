package com.rssl.phizic.business.mail;

import java.io.Serializable;

/**
 * Обертка для загрузки Enum'ов обращения в службу помощи в базу.
 * @author komarov
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class MailEnumLoader implements Serializable
{
	private String code;
	private String enumName;
	private String description;

	public MailEnumLoader()
	{}

	public MailEnumLoader(String code, String enumName, String description)
	{
		this.code = code;
		this.enumName = enumName;
		this.description = description;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getEnumName()
	{
		return enumName;
	}

	public void setEnumName(String enumName)
	{
		this.enumName = enumName;
	}
}
