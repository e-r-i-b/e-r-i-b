package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;

import java.util.List;

/**
 * @author vagin
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 * Форма окна множественного выбора ПУ при создании автоплатежа по свободным реквизитам
 */
public class FreeDetailAutoSubProvidersForm  extends ListPaymentServiceFormBase
{
	private List<BillingServiceProvider> providers;
	private ActivePerson activePerson;

	public List<BillingServiceProvider> getProviders()
	{
		return providers;
	}

	public void setProviders(List<BillingServiceProvider> providers)
	{
		this.providers = providers;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}
}
