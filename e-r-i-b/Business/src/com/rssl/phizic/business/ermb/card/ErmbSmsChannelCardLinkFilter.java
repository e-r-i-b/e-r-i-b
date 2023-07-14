package com.rssl.phizic.business.ermb.card;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import org.apache.commons.collections.Predicate;

/**
 * Выбор карт, доступных в смс-канале
 * Работает с линками
 * @author Puzikov
 * @ created 02.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbSmsChannelCardLinkFilter implements Predicate
{
	public boolean evaluate(Object object)
	{
		CardLink cardLink = (CardLink) object;
		Card card = cardLink.getCard();

		CardState cardState = card.getCardState();
		if (cardState == CardState.closed || cardState == CardState.blocked)
			return false;
		if (!cardLink.getShowInSms())
			return false;
		return true;
	}
}
