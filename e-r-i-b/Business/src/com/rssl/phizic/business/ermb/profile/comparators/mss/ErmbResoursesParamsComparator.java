package com.rssl.phizic.business.ermb.profile.comparators.mss;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 09.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор, проверяющий, изменились ли настройки видимости продуктов в СМС-канале
 * (важно количество линков и параметр отображения в СМС-канале)
 */
public class ErmbResoursesParamsComparator
{
	public int compare(Map<Class, List<ErmbProductLink>> oldProducts,  Map<Class, List<ErmbProductLink>> newProducts)
	{
		if (oldProducts == null ^ newProducts == null)
			return -1;
		
		// Сначала проверяем, совпадают ли размеры списков линков. Если не совпадают, значит, изменился состав продукт-линков
		List<ErmbProductLink> oldCards = oldProducts.get(CardLink.class);
		List<ErmbProductLink> newCards = newProducts.get(CardLink.class);
		if (oldCards.size() != newCards.size())
			return -1;

		List<ErmbProductLink> oldAccounts = oldProducts.get(AccountLink.class);
		List<ErmbProductLink> newAccounts = newProducts.get(AccountLink.class);
		if (oldAccounts.size() != newAccounts.size())
			return -1;

		List<ErmbProductLink> oldLoans = oldProducts.get(LoanLink.class);
		List<ErmbProductLink> newLoans = newProducts.get(LoanLink.class);
		if (oldLoans.size() != newLoans.size())
			return -1;

		// Если размеры списков соответствующих продуктов совпадают, проверяем значение параметра
		// "видимость в смс-канале" для каждого продукта
		if (!checkResoursesErmbViewChanges(oldProducts.get(CardLink.class), newProducts.get(CardLink.class)))
			return -1;

		if (!checkResoursesErmbViewChanges(oldProducts.get(AccountLink.class), newProducts.get(AccountLink.class)))
			return -1;

		if (!checkResoursesErmbViewChanges(oldProducts.get(LoanLink.class), newProducts.get(LoanLink.class)))
			return -1;

		return 0;
	}

	private boolean checkResoursesErmbViewChanges(List<? extends ErmbProductLink> oldLinks, List<? extends ErmbProductLink> newLinks)
	{
		Map<Long, Boolean> oldLinksNotifications = new HashMap<Long, Boolean>(oldLinks.size());
		for (ErmbProductLink link : oldLinks)
		{
			oldLinksNotifications.put(link.getId(), link.getErmbNotification());
		}

		for (ErmbProductLink link : newLinks)
		{
			if (oldLinksNotifications.get(link.getId()) != link.getErmbNotification())
				return false;
		}
		return true;
	}
}
