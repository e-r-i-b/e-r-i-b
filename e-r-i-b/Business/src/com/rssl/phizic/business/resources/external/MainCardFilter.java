package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author Erkin
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MainCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.isMain();
	}
}
