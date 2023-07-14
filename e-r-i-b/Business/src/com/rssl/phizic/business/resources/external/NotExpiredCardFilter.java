package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author Erkin
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отфильтровывает карты с истекшим сроком действия
 */
public class NotExpiredCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return DateHelper.getCurrentDate().before(card.getExpireDate());
	}
}
