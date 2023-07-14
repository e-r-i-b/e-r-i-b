package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Районы"
 */
public class Area extends MultiWordDictionaryEntry
{
	/**
	 * Код типа района
	 */
	private String typeOfArea;
	/**
	 * Код региона
	 */
	private String region;

	public String getTypeOfArea()
	{
		return typeOfArea;
	}

	public void setTypeOfArea(String typeOfArea)
	{
		this.typeOfArea = typeOfArea;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}
}
