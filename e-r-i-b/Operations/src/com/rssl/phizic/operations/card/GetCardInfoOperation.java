package com.rssl.phizic.operations.card;

import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.CardRestriction;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Krenev
 * @ created 09.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class GetCardInfoOperation extends OperationBase<CardRestriction> implements ViewEntityOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	protected CardLink cardLink;

	private boolean isUseStoredResource;

	public void initialize(Long cardId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		cardLink = personData.getCard(cardId);

		if (cardLink.getCard() instanceof AbstractStoredResource)
		{
			isUseStoredResource = true;
		}
	}

	public void initializeNew(Long cardId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		cardLink = personData.getCard(cardId);

		try
		{
			if (!personData.isCardDetailInfoUpdated(cardLink.getExternalId()))
			    cardLink.reset();
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}

		if (cardLink.getCard() instanceof AbstractStoredResource)
		{
			isUseStoredResource = true;
		}
	}

	/**
	 * @return карта из CardLink
	 */
	public Card getCard() throws BusinessException
	{
		if (cardLink == null)
			throw new BusinessException("Не установлен идентификатор карты");

		return cardLink.getCard();
	}

	public CardLink getEntity() throws BusinessException
	{
		if (cardLink == null)
			throw new BusinessException("Не установлен идентификатор карты");

		return cardLink;
	}

	/**
	 * @return Спецкарточный счет. Если счет не обнаружен, то кидаем BusinessException.
	 * @throws BusinessException
	 */                                 
	private Account getCardAcount() throws BusinessException, BusinessLogicException
	{
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(getCard()));
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @deprecated информацию получать по CardLink
	 */
	@Deprecated
	public Client getCardClient() throws BusinessException, BusinessLogicException
	{
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(getCard()));
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param card основная(ые) карта
	 * @return список "разрешенных" карт
	 * @throws BusinessException
	 */
	private List<CardLink> getAdditionalCards(CardLink card) throws BusinessException, BusinessLogicException
	{
		BankrollServiceHelper bankrollHelper = new BankrollServiceHelper();
		return bankrollHelper.getAllowedAdditionalCards(Collections.singletonList(card));
	}

	/**
	 * Получение информации по дополнительным картам.
	 *
	 * @return
	 * @throws BusinessException
	 */
	/*public Map<CardLink, CardInfo> getAdditionalCardsInfo() throws BusinessException, BusinessLogicException
	{
		Map<CardLink, Card> result = new HashMap<CardLink, Card>();

		Card card = getCard();

		if(MockHelper.isMockObject(card))
		{
			log.error("Невозможно получить информацию по доп.картам");
			return result;
		}

		if (!card.isMain())
		{
			return new HashMap<CardLink, Card>();//todo вообще-то это должно проверяться в эшене:(
		}
		CardRestriction restriction = getRestriction();
		List<CardLink> personCardLinks = getAdditionalCards(cardLink);
		List<Card> cards = new ArrayList<Card>();
		for (CardLink cardLink : personCardLinks)
		{
			Card personCard = cardLink.getValue();
			if(MockHelper.isMockObject(personCard))
			{
				log.error("Невозможно получить информацию по доп.карте "+cardLink.getNumber());
				continue;
			}
			if (restriction.accept(personCard))
			{
				cards.add(personCard);
			}
		}

		if(cards.size()!=0)
		{
			GroupResult<Card,CardInfo> gResult = bankrollService.getCardInfo((Card[])cards.toArray());
			for (CardLink personCardLink : personCardLinks)
			{
				Card personCard = personCardLink.getCard();
				if(!MockHelper.isMockObject(personCard))
				{
					CardInfo cardInfo = gResult.getResult(personCard);
					if(cardInfo!=null)
						result.put(personCardLink,cardInfo);
				}
			}
		}

		return result;
	}*/

	public Client getCardAccountClient() throws BusinessException, BusinessLogicException
	{
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(getCardAcount()));
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	/**
	 * Получение id кардлинка основной карты (только если принадлежит клиенту)
	 * @param cardLink - карта (дополнительная), для которой ищется номер основной
	 * @return id кардлинка основной карты
	 * @throws BusinessException
	 */
	public Long getMainCardId(CardLink cardLink) throws BusinessException
	{
		if (StringHelper.isEmpty(cardLink.getMainCardNumber()))
			return null;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		CardLink mainCard = personData.findCard(cardLink.getMainCardNumber());

		return mainCard == null ? null : mainCard.getId();
	}
}
