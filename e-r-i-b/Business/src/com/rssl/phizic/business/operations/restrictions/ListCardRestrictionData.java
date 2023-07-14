package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Класс данных ограничений для карт.
 *
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class ListCardRestrictionData extends RestrictionData <CardRestriction>
{
	private List<CardLink> cardLinks;

	public List<CardLink> getCardLinks()
	{
		return cardLinks;
	}

	public void setCardLinks(List<CardLink> cardLinks)
	{
		this.cardLinks = cardLinks;
	}

	public CardRestriction buildRestriction() throws BusinessException
	{
		Set<String> cards = new HashSet<String>();

		for (CardLink cardLink : cardLinks)
			cards.add(cardLink.getExternalId());

		return new CardRestrictionImpl(cards);
	}
}