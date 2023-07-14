package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author lepihina
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityRegionRelationComparator implements Comparator<DictionaryRecordBase>
{
	public int compare(DictionaryRecordBase record1, DictionaryRecordBase record2)
	{
		String region1 = ((CityRegionRelation)record1).getRegion();
		String region2 = ((City)record2).getRegionCode();

		return StringHelper.compare(region1, region2);
	}
}
