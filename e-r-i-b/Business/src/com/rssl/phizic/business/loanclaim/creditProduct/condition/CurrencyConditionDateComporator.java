package com.rssl.phizic.business.loanclaim.creditProduct.condition;

import java.util.Comparator;

/**
 * @author Moshenko
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyConditionDateComporator implements Comparator<CurrencyCreditProductCondition>
{
	public int compare(CurrencyCreditProductCondition codn1, CurrencyCreditProductCondition codn2)
	{
		int rc = codn1.getStartDate().compareTo(codn2.getStartDate());
		if (rc != 0)
			return rc;
		return codn1.getId().compareTo(codn2.getId());
	}
}
