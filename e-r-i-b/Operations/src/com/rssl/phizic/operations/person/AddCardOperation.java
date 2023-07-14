package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 14.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class AddCardOperation extends PersonOperationBase implements AddResourcesOperation<Card>
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static final GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
	private static final BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

	private List<CardLink> newCards;
	private List<CardLink> cardLinks;

	public void setPersonId(Long value) throws BusinessException, BusinessLogicException
	{
		super.setPersonId(value);
		initialize();
	}

	private void initialize() throws BusinessException, BusinessLogicException
	{
		cardLinks = externalResourceService.getLinks(getPerson().getLogin(), CardLink.class, getInstanceName());
		newCards = new ArrayList<CardLink>();
	}

	public void addResource(String cardId) throws BusinessException, BusinessLogicException
	{
		try
		{
			Card card = null;
			Office department = getPersonDepartment();

			if (InputMode.IMPORT == gateInfoService.getCardInputMode(department))
			{
				card = GroupResultHelper.getOneResult(bankrollService.getCard(cardId));
			}
			if(InputMode.MANUAL == gateInfoService.getAccountInputMode(department))
			{
				Pair<String, Office> cardInfo = new Pair<String, Office>(cardId, department);
				card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(getPerson().asClient(), cardInfo));
			}

			addResource(card);
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

	public void addResource(Card card) throws CardAlreadyExistsException
	{
		if (checkIfCardtExists(card.getNumber()))
			throw new CardAlreadyExistsException();

		CardLink newCardLink = new CardLink();
		newCardLink.setExternalId(card.getId());
		newCardLink.setNumber(card.getNumber());
		newCards.add(newCardLink);
	}

	public void addResources(List<Card> cards)
	{
		for (Card card : cards)
		{
			try
			{
				addResource(card);
			}
			catch (BusinessLogicException e)
			{
				//do nothing
			}
		}
	}

	@Transactional
	public String save() throws BusinessException, BusinessLogicException
	{
		String result = null;
		try
		{
			result = saveCards();
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		initialize();
		return result;
	}

	private String saveCards() throws BusinessException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Не удалось добавить следующие карты, т.к. не доступна АБС:");
		boolean isFirst = true;

		for (CardLink cardLink : newCards)
		{
			Card card = cardLink.getValue();
			if(!MockHelper.isMockObject(card))
				externalResourceService.addCardLink(getPerson().getLogin(), card, null, ErmbProductSettings.get(getPerson().getId()), null, null, getInstanceName());
			else
			{
				if(!isFirst)
					builder.append(",");
				else
				{
					isFirst = false;
				}
				 builder.append(cardLink.getNumber());
			}
		}
		if(isFirst)
			return null;
		else
			return builder.toString();
	}

	private boolean checkIfCardtExists(String cardId)
	{
		for (CardLink cardLink : cardLinks)
		{
			if (cardLink.getExternalId().equals(cardId))
				return true;
		}
		return false;
	}

	/**
	 * @return Список добавленных карт.
	 */
	public List<CardLink> getNewCards()
	{
		return newCards;
	}

	/**
	 * @return Список добавленных номеров карт
	 */
	public String getAddedNumbers() throws BusinessException
	{
		List<CardLink> list = newCards;
		StringBuffer result = new StringBuffer();

		if (list.size() > 0)
			result.append(list.get(0).getNumber());

		for (CardLink link : list)
		{
			result.append(link.getNumber());
		}

		return result.toString();
	}
}
