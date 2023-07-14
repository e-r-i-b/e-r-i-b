package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.Card;

/**
 * Фильтр, пропускающий все карты, которые являются основными или дополнительными картами, выпущенными на имя держателя основной карты (т.е. с типом CLIENTTOCLIENT).
 * @author lepihina
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
public class FullOwnCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.getAdditionalCardType() == null || card.getAdditionalCardType() == AdditionalCardType.CLIENTTOCLIENT;
	}
}
