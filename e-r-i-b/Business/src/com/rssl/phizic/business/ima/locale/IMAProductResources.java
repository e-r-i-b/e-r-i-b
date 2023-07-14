package com.rssl.phizic.business.ima.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Локализованные ресурсы для описания обезличенного металлического счета
 * @author lepihina
 * @ created 12.06.15
 * $Author$
 * $Revision$
 */
public class IMAProductResources extends LanguageResource
{
	private String name;

	/**
	 * @return название счета ОМС
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name название счета ОМС
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
