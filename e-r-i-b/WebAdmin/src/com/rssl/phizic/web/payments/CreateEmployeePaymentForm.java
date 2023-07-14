package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;

/**
 * @author khudyakov
 * @ created 14.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateEmployeePaymentForm extends CreatePaymentForm
{
	private ActivePerson activePerson;
	private boolean modified;
	private String autoSubNumber;

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

	public String getAutoSubNumber()
	{
		return autoSubNumber;
	}

	public void setAutoSubNumber(String autoSubNumber)
	{
		this.autoSubNumber = autoSubNumber;
	}
}
