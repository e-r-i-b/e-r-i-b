package com.rssl.phizic.web.util;

/**
 * @author Rydvanskiy
 * @ created 15.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum UserAgent
{
	mobile("Mobile Device", "_mobile"),
	web("", "_mobile");  // для деактивации веб версии

	private String prefix;
	private String name;

	UserAgent(String name, String prefix)
	{
		this.prefix = prefix;
		this.name = name;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public String getName()
	{
		return name;
	}
}
