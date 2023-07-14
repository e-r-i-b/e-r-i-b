package com.rssl.phizic.business.dictionaries.regions.replication;

import com.rssl.phizic.business.dictionaries.regions.Region;

import java.util.Comparator;

/**
 * @author khudyakov
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class RegionsComparator implements Comparator<Region>
{
	public int compare(Region region, Region anotherRegion)
	{		
		int compareResult;
		if ((compareResult = region.getSynchKey().compareTo(anotherRegion.getSynchKey())) != 0)
			return compareResult;

		if ((compareResult = region.getName().compareTo(anotherRegion.getName()))!=0)
			return compareResult;

		String codeTB = region.getCodeTB();
		String anotherCodeTB = anotherRegion.getCodeTB();

		if (codeTB == null && anotherCodeTB == null)
			return 0;

		if  (codeTB == null)
			return -1;

		if (anotherCodeTB == null)
			return 1;

		return codeTB.compareTo(anotherCodeTB);
	}
}
