package com.rssl.phizic.business.dictionaries.currencies.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResources;

/**
 * Локализованные ресурсы для курса валют
 * @author lepihina
 * @ created 09.06.15
 * $Author$
 * $Revision$
 */
public class CurrencyImplResources implements LanguageResources<String>
{
	private String id;
	private String localeId;
	private String name;

	/**
	 * @return идентификатор валюты из справочника валют
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор валюты из справочника валют
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return локаль
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId локаль
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return название валюты
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name название валюты
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
