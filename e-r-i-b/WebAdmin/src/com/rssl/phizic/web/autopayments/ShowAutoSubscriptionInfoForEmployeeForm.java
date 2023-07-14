package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;

/**
 * @author bogdanov
 * @ created 09.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionInfoForEmployeeForm extends ShowAutoSubscriptionInfoForm
{
	private ActivePerson activePerson;
	private boolean modified;
	private String paymentType;            //строковая константа, используемая для обозначения типа автоплатежа

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

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}
}
