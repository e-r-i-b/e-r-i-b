package com.rssl.phizic.business.security.auth;

import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.GuestPersonData;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.guest.GuestCardLink;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balovtsev
 * @since  06.05.2015.
 */
public class GuestLoadCardsAction implements AthenticationCompleteAction
{
	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		GuestPersonData guestData = (GuestPersonData) PersonContext.getPersonDataProvider().getPersonData();

		if (guestData.getGuestType() == GuestType.UNREGISTERED)
		{
			return;
		}

		try
		{
			List<CardLink> clientCards = getClientCards(guestData);
			guestData.setCards(clientCards);
			if (CardsUtil.hasClientActiveCreditCard(clientCards))
			{
				guestData.setLoanCardClaimAvailable(false);
			}
		}
		catch (LogicException e)
		{
			throw new SecurityLogicException(e);
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	private List<CardLink> getClientCards(GuestPersonData personData) throws BusinessException, GateLogicException, GateException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		Client client = personData.getPerson().asClient();
		client.getOffice().setCode(new CodeImpl("99", "", ""));
		List<Card> clientCards = bankrollService.getClientCards(client);

		List<CardLink> links = new ArrayList<CardLink>();
		for (Card clientCard : clientCards)
		{
			GuestCardLink link = new GuestCardLink();
			link.setCard(clientCard);
			link.setNumber(clientCard.getNumber());
			link.setCurrency(clientCard.getCurrency());
			link.setExpireDate(clientCard.getExpireDate());
			link.setCardPrimaryAccount(clientCard.getPrimaryAccountNumber());
			links.add(link);
		}

		return links;
	}
}
