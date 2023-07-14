package com.rssl.phizic.business.ermb.card;

import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.CardFilterBase;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.utils.MockHelper;

/**
 * Выбор карты доступной для использования в качестве платежной для ЕРМБ
 * Работает с линками
 * @author Puzikov
 * @ created 02.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbPaymentCardLinkFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		if (MockHelper.isMockObject(card))
			return false;
		if (CardsUtil.isCreditCard(card))
			return false;
		if (card.isVirtual())
			return false;
		if (CardsUtil.isCorporate(card.getNumber()))
			return false;
		if (card.getAdditionalCardType() != null)
			return false;
		if (card.getCardState() != CardState.active)
			return false;
		if (card.getCardAccountState() == AccountState.ARRESTED)
			return false;
		return true;
	}
}
