package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionPaymentInfoForm;

/**
 * @author bogdanov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionPaymentInfoForEmployeeForm extends ShowAutoSubscriptionPaymentInfoForm
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