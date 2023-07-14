package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;

/**
 * Ôטכüענ הכÿ מעבמנא הובועמגûץ ךאנע
 * @author lepihina
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class DebitCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return CardType.debit == card.getCardType();
	}
}