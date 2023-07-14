package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/**
 * Фильтр, отбирающий заблокированные карты.
 *
 * @author bogdanov
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class BlockedCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.getCardState() == CardState.blocked;
	}
}
