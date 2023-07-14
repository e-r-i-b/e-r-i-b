package com.rssl.phizic.operations.loyaltyProgram;

import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;

import java.util.Comparator;

/**
 * Используется для сортировки операций по бонусному счету в порядке убывания даты
 * @author lukina
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramOperationsComparator implements Comparator<LoyaltyProgramOperation>
{
	public int compare(LoyaltyProgramOperation o1, LoyaltyProgramOperation o2)
	{
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;
		return o2.getOperationDate().compareTo(o1.getOperationDate());
	}
}
