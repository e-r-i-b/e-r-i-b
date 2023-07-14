package com.rssl.phizic.web.payments;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.business.persons.ActivePerson;

/**
 * @author hudyakov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmEmployeePaymentByFormForm extends ConfirmPaymentByFormForm
{
	private ActivePerson activePerson;
	private boolean modified;

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
}
