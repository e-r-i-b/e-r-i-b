package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Компаратор сортирует cardLink'и в порядке возрастания даты окончания срока действия
 *
 @author: Egorovaa
 @ created: 17.09.2012
 @ $Author$
 @ $Revision$
 */
public class CardLinkDateComparator extends ProductComparator  implements Comparator<CardLink>
{
	public int compare(CardLink o1, CardLink o2)
	{
		Calendar clendar1 = o1.getExpireDate();
		Calendar clendar2 = o2.getExpireDate();

		//приоритетнее карта с более поздней датой закрытия
		if (clendar1 == null || clendar2 == null)
		{
			return compareToNull(clendar1, clendar2);
		}
		else
			return clendar1.compareTo(clendar2);
	}
}
