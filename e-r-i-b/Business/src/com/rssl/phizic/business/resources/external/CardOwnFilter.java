package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.cards.CardImpl;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.person.Person;

/**
 * Является ли карта собственной картой клиента.
 *
 * @author bogdanov
 * @ created 04.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardOwnFilter extends CardFilterBase
{
	private Person person;

	/**
	 * ctor
	 */
	public CardOwnFilter()
	{
	}

	/**
	 * ctor
	 * @param person
	 */
	public CardOwnFilter(Person person)
	{
		this.person = person;
	}

	public boolean evaluate(Object object)
	{
		if (!(object instanceof CardLink))
			return false;

		CardLink cardlink = (CardLink) object;

		return cardlink.isMain() || AdditionalCardType.CLIENTTOOTHER != cardlink.getAdditionalCardType();
	}

	public boolean accept(Card card)
	{
		if (card instanceof CardImpl)
		{
			return card.isMain() || AdditionalCardType.CLIENTTOOTHER != card.getAdditionalCardType();
		}

		CardLink link = CardsUtil.getCardLink(card.getNumber());
		if (link != null)
			return link.isMain() || AdditionalCardType.CLIENTTOOTHER != link.getAdditionalCardType();

		return new CardOwnFIOFilter(person).accept(card);
	}
}
