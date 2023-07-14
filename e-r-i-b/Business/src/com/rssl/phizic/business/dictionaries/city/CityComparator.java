package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author lepihina
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityComparator implements Comparator<City>
{
	public int compare(City city1, City city2)
	{
		if (city1 == null && city2 == null)
			return 0;

		if (city1 == null || city2 == null)
			return -1;

		int compareResult = city1.getCode().compareTo(city2.getCode());
		if(compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(city1.getName(), city2.getName());
		if(compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(city1.getRegionCode(), city2.getRegionCode());
		if(compareResult != 0)
			return compareResult;

		return 0;
	}
}
