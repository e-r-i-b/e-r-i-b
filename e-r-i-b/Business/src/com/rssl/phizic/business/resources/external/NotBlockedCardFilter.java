package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/** ‘ильтр, провер€ющий не заблокированность карты
 * @author akrenev
 * @ created 26.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotBlockedCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.getCardState() != CardState.blocked;
	}
}
