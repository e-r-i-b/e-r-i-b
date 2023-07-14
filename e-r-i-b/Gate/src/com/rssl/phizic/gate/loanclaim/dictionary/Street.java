package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 */

import java.math.BigInteger;

/**
 * Запись справочника "Улицы"
 */
public class Street extends MultiWordDictionaryEntry
{
	/**
	 * Код типа улицы
	 */
	private String typeOfStreet;
	/**
	 * Код населенного пункта
	 */
	private BigInteger settlement;
	/**
	 * Код города
	 */
	private BigInteger city;
	/**
	 * Код района
	 */
	private BigInteger area;
	/**
	 * Код региона
	 */
	private String region;

	public String getTypeOfStreet()
	{
		return typeOfStreet;
	}

	public void setTypeOfStreet(String typeOfStreet)
	{
		this.typeOfStreet = typeOfStreet;
	}

	public BigInteger getSettlement()
	{
		return settlement;
	}

	public void setSettlement(BigInteger settlement)
	{
		this.settlement = settlement;
	}

	public BigInteger getCity()
	{
		return city;
	}

	public void setCity(BigInteger city)
	{
		this.city = city;
	}

	public BigInteger getArea()
	{
		return area;
	}

	public void setArea(BigInteger area)
	{
		this.area = area;
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
