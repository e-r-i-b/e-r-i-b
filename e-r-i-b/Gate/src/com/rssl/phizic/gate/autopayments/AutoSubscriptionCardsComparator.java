package com.rssl.phizic.gate.autopayments;

import com.rssl.phizic.gate.bankroll.Card;

import java.util.Comparator;

/**
 * Comparator автоподписок для сортировки по ИД
 * @author Jatsky
 * @ created 12.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class AutoSubscriptionCardsComparator implements Comparator<Card>
{
	public int compare(Card o1, Card o2)
	{
		return o1.getId().compareTo(o2.getId());
	}
}
