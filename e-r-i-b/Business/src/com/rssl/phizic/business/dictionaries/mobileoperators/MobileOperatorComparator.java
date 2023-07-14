package com.rssl.phizic.business.dictionaries.mobileoperators;

import com.rssl.phizic.business.mobileOperators.MobileOperator;

import java.util.Comparator;

/**
 * @author Dorzhinov
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileOperatorComparator implements Comparator<MobileOperator>
{
	public int compare(MobileOperator o1, MobileOperator o2)
	{
		int compareResult = o1.getCode().compareTo(o2.getCode());
		if(compareResult == 0)
			return o1.getName().compareTo(o2.getName());
		return compareResult;
	}
}
