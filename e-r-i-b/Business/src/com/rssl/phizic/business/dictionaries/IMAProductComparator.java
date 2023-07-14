package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.ima.IMAProduct;

import java.util.Comparator;

/**
 *  омпаратор дл€ сравнени€ записей справочника обезличенных металлических счетов.
 * @author Pankin
 * @ created 18.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAProductComparator implements Comparator<IMAProduct>
{
	public int compare(IMAProduct o1, IMAProduct o2)
	{
		int compareResult = o1.getType().compareTo(o2.getType());
		if (compareResult == 0)
		{
			compareResult = o1.getSubType().compareTo(o2.getSubType());
		}
		if (compareResult == 0)
		{
			compareResult = o1.getName().compareTo(o2.getName());
		}
		if (compareResult == 0)
		{
			compareResult = o1.getContractTemplate().compareTo(o2.getContractTemplate());
		}
		return compareResult;
	}
}
