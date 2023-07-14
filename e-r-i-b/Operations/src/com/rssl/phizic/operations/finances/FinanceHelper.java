package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lepihina
 * @ created 27.05.14
 * $Author$
 * $Revision$
 * Хэлпер для работы с "Моими финансами"
 */
public class FinanceHelper
{
	public static final String CARD_IDS_SEPARATOR = ";";
	public static final Long CASH_PAYMENTS_ID = -1L;

	/**
	 * Формирует список из номеров карт
	 * @param cards - карты
	 * @return список из номеров карт
	 */
	public static List<String> getCardNumbersList(List<CardLink> cards)
	{
		List<String> cardNumbers = new ArrayList<String>(cards.size());
		for (CardLink cardLink: cards)
		{
			cardNumbers.add(cardLink.getNumber());
		}
		return cardNumbers;
	}

	/**
	 * Возвращает список из номеров карт
	 * @param cards - список карт
	 * @param selectedIds - идентификаторы карт
	 * @return список из номеров карт
	 */
	public static List<String> getCardNumbersList(List<CardLink> cards, String selectedIds)
	{
		List<String> cardNumbers = new ArrayList<String>();
		if (StringHelper.isEmpty(selectedIds))
			return cardNumbers;

		String selectedCardIds = selectedIds.replaceAll(CASH_PAYMENTS_ID.toString() + CARD_IDS_SEPARATOR, "");
		if (StringHelper.isEmpty(selectedCardIds))
			return cardNumbers;

		String[] stringCardIds = selectedCardIds.split(CARD_IDS_SEPARATOR);
		for(String id : stringCardIds)
		{
			for(CardLink card : cards)
			{
				if (card.getId().equals(Long.parseLong(id)))
				{
					cardNumbers.add(card.getNumber());
					break;
				}
			}
		}
		return cardNumbers;
	}

	/**
	 * Возвращает список из карт
	 * @param cardIds - идентификаторы карт
	 * @return список из карт
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static List<CardLink> getSelectedCards(String cardIds) throws BusinessLogicException, BusinessException
	{
		return getSelectedCards(PersonContext.getPersonDataProvider().getPersonData().getCards(), cardIds);
	}

	/**
	 * Возвращает список из карт
	 * @param cardLinks - список карт
	 * @param selectedIds - идентификаторы карт
	 * @return список из карт
	 */
	public static List<CardLink> getSelectedCards(List<CardLink> cardLinks, String selectedIds) throws BusinessLogicException, BusinessException
	{
		String[] stringCardIds = selectedIds.replaceAll(CASH_PAYMENTS_ID.toString() + CARD_IDS_SEPARATOR, "").split(CARD_IDS_SEPARATOR);
		List<CardLink> selectedCards = new ArrayList<CardLink>();
		if (stringCardIds.length == 0)
		{
			return selectedCards;
		}

		for(String id : stringCardIds)
		{
			for(CardLink card : cardLinks)
			{
				if (card.getId().equals(Long.parseLong(id)))
				{
					selectedCards.add(card);
					break;
				}
			}
		}
		return selectedCards;
	}
}
