package com.rssl.phizic.business.dictionaries.defCodes;

import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author Rtischeva
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */
public class DefCodeComparator implements Comparator<DefCode>
{
	public int compare(DefCode o1, DefCode o2)
	{
		if (o1 == null && o2 == null)
			return 0;

		if (o1 == null || o2 == null)
			return -1;

		int compareResult = StringHelper.compare(o1.getDefCodeFrom(), o2.getDefCodeFrom());
		if(compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(o1.getDefCodeTo(), o2.getDefCodeTo());
		if(compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(o1.getProviderCode(), o2.getProviderCode());
		if(compareResult != 0)
			return compareResult;

		compareResult = o1.getMnc().compareTo(o2.getMnc());
		if(compareResult != 0)
			return compareResult;

		return 0;
	}
}
