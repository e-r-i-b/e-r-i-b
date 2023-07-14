package com.rssl.phizic.business.businessProperties;

/**
 * Бизнес настройки
 * @author lukina
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class BusinessProperty
{
	private Long id;
	private String key;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
}
