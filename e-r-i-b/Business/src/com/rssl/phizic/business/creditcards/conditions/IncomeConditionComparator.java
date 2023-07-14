package com.rssl.phizic.business.creditcards.conditions;

import com.rssl.phizic.business.loans.CurrencyRurUsdEuroComparator;

import java.util.Comparator;

/**
 * @author Dorzhinov
 * @ created 30.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class IncomeConditionComparator implements Comparator<IncomeCondition>
{
	public int compare(IncomeCondition condition1, IncomeCondition condition2)
	{
		CurrencyRurUsdEuroComparator comparator = new CurrencyRurUsdEuroComparator();
		return comparator.compare(condition1.getCurrency(), condition2.getCurrency());
	}
}
