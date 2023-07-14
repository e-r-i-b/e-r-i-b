package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/**
 * Фильтр, отбирающий заблокированные и закрытые карты.
 * @ author gorshkov
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedBlockedCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.getCardState() == CardState.blocked || card.getCardState() == CardState.closed || card.getCardState() == CardState.replenishment || card.getCardState() == CardState.changing;
	}
}

