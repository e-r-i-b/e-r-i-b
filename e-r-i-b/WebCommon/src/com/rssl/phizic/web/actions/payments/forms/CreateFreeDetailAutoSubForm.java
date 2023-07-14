package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.persons.ActivePerson;

/**
 * @author vagin
 * @ created 06.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateFreeDetailAutoSubForm extends EditJurPaymentForm
{
	private ActivePerson activePerson;

	/**
	 * Проинициализированый в контексте ActivePerson
	 * @return activePerson
	 */
	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}
}
