package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.common.AbstractCompatator;

/**
 * @author Kosyakova
 * @ created 14.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class CountriesComparator extends AbstractCompatator
{
	public int compare(Object o1, Object o2)
	{
		ContactCountry member1 = (ContactCountry) o1;
		ContactCountry member2 = (ContactCountry) o2;

		if(!isObjectsEquals(member1.getSynchKey(),member2.getSynchKey()))
			return -1;

		if(!isObjectsEquals(member1.getCode(),member2.getCode()))
			return -1;

		if(!isObjectsEquals(member1.getName(),member2.getName()))
			return -1;

		if(!isObjectsEquals(member1.getNameLat(),member2.getNameLat()))
			return -1;

		return 0;
	}
}
