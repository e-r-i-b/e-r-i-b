package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.persons.ActivePerson;

/**
 * Форма для создания шинного автоплатежа
 * @author niculichev
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoSubscriptionPaymentForm extends EditServicePaymentForm
{
	private ActivePerson activePerson;
	private boolean modified;
	private boolean createLongOffer = false;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	public boolean isCreateLongOffer()
	{
		return createLongOffer;
	}

	public void setCreateLongOffer(boolean createLongOffer)
	{
		this.createLongOffer = createLongOffer;
	}
}
