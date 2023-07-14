package com.rssl.phizic.business;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * ������ ��� ������ � ���������� � ��� ����������.
 * @author egorova
 * @ created 17.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class BankrollServiceHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);


	/**
	 * todo. ��������� �����. �� ����������� ����� � WAY4.
	 * ��������� ������ ���. ���� �� ��������
	 * @param mainCard - ������ �������� ����
	 * @return ������ ���. ���� � ������ ����������
	 */
	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		GroupResult<Card, List<Card>> res = new GroupResult<Card, List<Card>>();
		if (!(mainCard != null && mainCard.length > 0))
			return res;

		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			Client client = clientService.getClientById(person.getClientId());
//�������� ��� ����� �������
			List<Card> cards = bankrollService.getClientCards(client);
			if (cards.isEmpty())
				return GroupResultHelper.getOneErrorResult(mainCard, new GateException("���������� �� ������ �� ��������."));
			for (Card currentMainCard : mainCard)
			{
				List<Card> additionalCards = new ArrayList<Card>();
				for (Card card : cards)
				{
					if (card.getMainCardNumber().equals(currentMainCard.getNumber()))
						additionalCards.add(card);
				}
				if (!additionalCards.isEmpty())
					cards.removeAll(additionalCards);
				res.putResult(currentMainCard, additionalCards);
			}
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, e);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, e);
		}
		return res;
	}

	/**
	 * �������� ����� ������ ���� �������������� � ���������� ��������(��) ���� ������� � ������ ����������
	 * @param cards - ��������(��) �����(�)
	 * @return ����� ������ ���� �������������� ���� �������
	 */
	public List<CardLink> getAllowedAdditionalCards(List<CardLink> cards)
	{
		boolean useLinks = ConfigFactory.getConfig(ESBEribConfig.class).getAdditionCardsFromLink();
		if (useLinks)
		{
			return getAdditionalCardsUsingLinks(cards);
		}

		//TODO: ������ � ������ ��������� ������������� GFL � ������ ��� ��������� ���. ���� ENH025301
		List<CardLink> result = new ArrayList<CardLink>();
		try
		{
			List<Card> additionalCards = getAllAdditionalCards(getCardsFromLinks(cards));
			if (additionalCards.isEmpty())
				return result;
			for (Card additionalCard : additionalCards)
			{
				//��������� ��������� ����� ��� ��� (������� ���� �� � PersonData)
				CardLink cardLink = findCardLink(additionalCard.getNumber());
				if (cardLink != null)
				{
					result.add(cardLink);
				}
			}
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			return result;
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
			return result;
		}
		return result;
	}

	private List<CardLink> getAdditionalCardsUsingLinks(List<CardLink> mainCards)
	{
		List<CardLink> result = new ArrayList<CardLink>();
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			List<CardLink> personCards = personData.getCards();
			for (CardLink mainCard : mainCards)
			{
				for (CardLink card : personCards)
				{
					if (!card.isMain() && mainCard.getNumber().equals(card.getMainCardNumber()))
						result.add(card);
				}
			}
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return result;
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			return result;
		}
		return result;
	}

	private Card[] getCardsFromLinks(List<CardLink> cards)
	{
		String[] ids = new String[cards.size()];
		for (int i = 0; i < cards.size(); i++)
		{
			ids[i] = cards.get(i).getExternalId();
		}

		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		List<Card> cardList = GroupResultHelper.getResults(bankrollService.getCard(ids));
		return cardList.toArray(new Card[cardList.size()]);
	}
	
	/*
	��������� ���� ������ �� ����� ��� �������� �������
	 */
	private CardLink findCardLink(String number)
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		try
		{
			return provider.getPersonData().findCard(number);
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������ ����", e);
			return null;
		}
	}

	/*
	 ��������� ���� �������������� ���� � ���������
	 */
	private List<Card> getAllAdditionalCards(Card... mainCard) throws GateException, GateLogicException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		return GroupResultHelper.joinValues(bankrollService.getAdditionalCards(mainCard));
	}

	/*
	����� ������ �� ����� �� ������ �����
	 */
	public static CardLink findCardLinkByNumber(String cardNumber, List<CardLink> cardLinks)
	{
		for (CardLink cardLink : cardLinks)
		{
			if (cardLink.getNumber().equals(cardNumber))
				return cardLink;
		}
		return null;
	}

		/*
	����� ������ �� ���� �� ������ �����
	 */
	public static AccountLink findAccountLinkByNumber(String accountNumber, List<AccountLink> accountLinks)
	{
		for (AccountLink link : accountLinks)
		{
			if (link.getNumber().equals(accountNumber))
				return link;
		}
		return null;
	}
}
