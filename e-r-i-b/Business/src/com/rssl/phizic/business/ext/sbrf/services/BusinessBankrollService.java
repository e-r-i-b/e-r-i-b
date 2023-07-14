package com.rssl.phizic.business.ext.sbrf.services;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizicgate.esberibgate.bankroll.CardImpl;

import java.util.List;

/**
 * @author niculichev
 * @ created 20.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class BusinessBankrollService extends com.rssl.phizic.business.BusinessBankrollService
{
	public BusinessBankrollService(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public GroupResult<String, Card> getCard(String... cardId)
	{
		boolean accessArrested = PermissionUtil.impliesServiceRigid("ArrestedCardsInfo");
		GroupResult<String, Card> result = delegate.getCard(cardId);
		if (!accessArrested)
			for (Card card : result.getResults().values())
			{
				if (AccountState.ARRESTED == card.getCardAccountState())
					((CardImpl)card).setCardAccountState(null);
			}
		return result;
	}

	/*
	В сбере карточки в шине, искать по дополнительным идентификаторам не нужно,
	т.к идентификация клиента происходит по ФИО и ДУЛ
	*/
	@Override
	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		boolean accessArrested = PermissionUtil.impliesServiceRigid("ArrestedCardsInfo");
		List<Card> cards = delegate.getClientCards(client);

		//если нет прав на арестованные карты, удаляем из получаемых карт
		if (!accessArrested)
			for (Card card : cards)
			{
				if (AccountState.ARRESTED == card.getCardAccountState())
					((CardImpl)card).setCardAccountState(null);
			}
		return cards;
	}

}
