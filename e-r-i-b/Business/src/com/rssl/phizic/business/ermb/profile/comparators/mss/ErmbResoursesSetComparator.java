package com.rssl.phizic.business.ermb.profile.comparators.mss;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;

import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор, проверяющий, изменился ли список линков продуктов клиента, отображаемых в СМС-канале
 * (важно количество линков и их идентификаторы, т.к. при изменении продукта меняется ID линка)
 */
public class ErmbResoursesSetComparator 
{
	public int compare(Map<Class, List<Long>> oldProducts, Map<Class, List<Long>> newProducts)
	{
		if (oldProducts == null ^ newProducts == null)
			return -1;

		List<Long> oldCards = oldProducts.get(CardLink.class);
		List<Long> newCards = newProducts.get(CardLink.class);
		if (!(oldCards.size() == newCards.size() && oldCards.containsAll(newCards)))
			return -1;

		List<Long> oldAccounts = oldProducts.get(AccountLink.class);
		List<Long> newAccounts = newProducts.get(AccountLink.class);
		if (!(oldAccounts.size() == newAccounts.size() && oldAccounts.containsAll(newAccounts)))
			return -1;

		List<Long> oldLoans = oldProducts.get(LoanLink.class);
		List<Long> newLoans= newProducts.get(LoanLink.class);
		if (!(oldLoans.size() == newLoans.size() && oldLoans.containsAll(newLoans)))
			return -1;

		return 0;
	}
}
