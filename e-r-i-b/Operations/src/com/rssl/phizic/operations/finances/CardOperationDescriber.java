package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.ListTransformer;

import java.util.*;

/**
 * @author Erkin
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Строит для карточной операции расширенное описание
 */
class CardOperationDescriber implements ListTransformer<CardOperationDescription, CardOperation>
{
	private static final CardOperationService cardOperationService = new CardOperationService();

	private final Currency nationalCurrency;

	private final Map<String, Card> cardsByNumber;

	private final List<CardOperationCategory> categories;


	CardOperationDescriber(Collection<CardLink> cardLinks, Collection<CardOperationCategory> categories) throws BusinessException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		try
		{
			this.nationalCurrency = currencyService.getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}

		this.categories = new ArrayList<CardOperationCategory>(categories);
		this.cardsByNumber = CardLinkHelper.mapCardsByNumber(cardLinks);
	}

	public List<CardOperationDescription> transform(List<CardOperation> operations) throws BusinessException
	{
		List<CardOperationDescription> result = new ArrayList<CardOperationDescription>(operations.size());
		for (CardOperation operation : operations)
		{
			Card card = null;
			Money cardAmount = null;
			if (operation.getOperationType() == OperationType.BY_CARD)
			{
				card = cardsByNumber.get(operation.getCardNumber());
				if (card == null)
					throw new BusinessException("Не найдена карта для карточной операции " + operation);
				if (operation.getCardAmount() != null)
					cardAmount = new Money(operation.getCardAmount(), card.getCurrency());
			}
			Money nationalAmount = new Money(operation.getNationalAmount(), nationalCurrency);
			List<CardOperationCategory> availableCategories = selectOperationAvailableCategories(operation);

			CardOperationDescription description = new CardOperationDescription();
			description.setOperation(operation);
			description.setCard(card);
			description.setCardAmount(cardAmount);
			description.setNationalAmount(nationalAmount);
			description.setAvailableCategories(availableCategories);
			result.add(description);
		}
		return result;
	}

	private List<CardOperationCategory> selectOperationAvailableCategories(CardOperation operation) throws BusinessException
	{
		Set<CardOperationCategory> set = new LinkedHashSet<CardOperationCategory>();
		set.add(operation.getCategory());
		for (CardOperationCategory category : categories)
		{
			if (cardOperationService.testOperationCompatibleToCategory(operation, category))
				set.add(category);
		}
		return new ArrayList<CardOperationCategory>(set);
	}
}
