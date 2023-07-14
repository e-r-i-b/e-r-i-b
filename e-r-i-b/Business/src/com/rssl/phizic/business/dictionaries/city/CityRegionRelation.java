package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author lepihina
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityRegionRelation extends DictionaryRecordBase
{
	private String city;
	private String region;

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public Comparable getSynchKey()
	{
		return city;
	}
}
