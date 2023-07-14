package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;

/**
 * Фильтр для отбора кредитных карт
 * @author lepihina
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return CardType.credit == card.getCardType();
	}
}
