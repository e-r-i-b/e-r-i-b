package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;

import java.util.Comparator;

/**
 * @author Mescheryakova
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class CASNSICardProductComparator implements Comparator<CASNSICardProduct>
{
	public int compare(CASNSICardProduct o1, CASNSICardProduct o2)
	{
		int compareResult = (o1.getSynchKey()).compareTo(o2.getSynchKey());
		if (compareResult == 0)
		{
			compareResult = o1.getName().compareTo(o2.getName());
			if (compareResult == 0)
				return (o1.getStopOpenDeposit()).compareTo(o2.getStopOpenDeposit());
		}
		return compareResult;
	}
}
