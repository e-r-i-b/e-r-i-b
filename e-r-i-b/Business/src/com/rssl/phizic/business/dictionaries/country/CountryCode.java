package com.rssl.phizic.business.dictionaries.country;


/**
 * Сущность страны с iso 3166 - кодами
 * @author koptyaev
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */
public class CountryCode
{
	private String iso2;
	private String iso3;
	private String name;

	/**
	 * @return iso2 код
	 */
	public String getIso2()
	{
		return iso2;
	}

	/**
	 * Установить iso2 код записи
	 * @param iso2 код
	 */
	public void setIso2(String iso2)
	{
		this.iso2 = iso2;
	}

	/**
	 * @return iso3 код
	 */
	public String getIso3()
	{
		return iso3;
	}

	/**
	 * Установить iso3 код
	 * @param iso3 код
	 */
	public void setIso3(String iso3)
	{
		this.iso3 = iso3;
	}

	/**
	 * Название страны
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Установить название записи
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
