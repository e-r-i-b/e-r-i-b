package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CreditCardFilter;
import com.rssl.phizic.business.resources.external.NotVirtualCardsFilter;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.security.PermissionUtil;

/**
 * Фильтр для показа списка карт на первом шаге при перевыпуске.
 * В фильтре не производится проверка параметров UNICardType и UNIAcctType
 *
 * @author basharin
 * @ created 27.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissuedListCardFilter extends ReissuedCardFilter
{
	private final CreditCardFilter creditCardFilter = new CreditCardFilter();
	private final NotVirtualCardsFilter notVirtualCardsFilter = new NotVirtualCardsFilter();

	@Override
	public boolean evaluate(Object object)
	{
		if (!(object instanceof CardLink))
			return false;

		CardLink cardLink = (CardLink) object;
		Card card = cardLink.getCard();

		return !creditCardFilter.accept(card) && (PermissionUtil.impliesService("ReIssueCardIncludeVirtualClaim") || notVirtualCardsFilter.accept(card)) && card.isMain()
				&& (isBlocked(card) || CardState.active == card.getCardState());
	}

}
