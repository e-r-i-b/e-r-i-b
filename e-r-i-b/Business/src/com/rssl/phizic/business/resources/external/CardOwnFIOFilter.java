package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.context.PersonContext;

/**
 * Фильтр сравнивает полное имя держателя карты и текущего (либо указанного) клиента
 *
 * @author khudyakov
 * @ created 03.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardOwnFIOFilter extends CardFilterBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Person person;

	/**
	 * ctor
	 */
	public CardOwnFIOFilter()
	{
	}

	/**
	 * ctor
	 * @param person
	 */
	public CardOwnFIOFilter(Person person)
	{
		this.person = person;
	}

	public boolean accept(Card card)
	{
		Person tmpPerson = person != null ? person : PersonContext.getPersonDataProvider().getPersonData().getPerson();

		return card.getHolderName().equalsIgnoreCase(tmpPerson.getFullName());
	}
}
