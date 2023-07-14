package com.rssl.phizic.web.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.payments.PersonalPaymentCard;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 23.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetPersonalPaymentCardForm extends ActionFormBase
{
	public static final Form FILTER_FORM = FormBuilder.EMPTY_FORM;

	private Long person;

	private ActivePerson activePerson;

	private PersonalPaymentCard card
			= new PersonalPaymentCard();

	private boolean modified;

	///////////////////////////////////////////////////////////////////////////

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public PersonalPaymentCard getCard()
	{
		return card;
	}

	public void setCard(PersonalPaymentCard card)
	{
		if (card == null)
			this.card = new PersonalPaymentCard();
		this.card = card;
	}

	public List<PersonalPaymentCard> getCardAsList()
	{
		return Collections.singletonList(card);
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
}
