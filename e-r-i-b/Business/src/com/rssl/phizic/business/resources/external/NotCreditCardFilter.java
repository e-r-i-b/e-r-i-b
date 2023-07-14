package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;

/**
 * @author osminin
 * @ created 17.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class NotCreditCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return !CardType.credit.equals(card.getCardType());
	}
}
