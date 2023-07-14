package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.clients.list.ClientInformation;
import com.rssl.phizic.web.persons.SearchPersonForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nady
 * @ created 01.04.2015
 * @ $Author$
 * @ $Revision$
 */
/**
 * Форма для поиска клиента для создания заявки на кредит в АРМ Сотрудника
 */
public class LoanClaimCreateForm extends SearchPersonForm
{
	private boolean isGuestPerson;
	private List<ClientInformation> activePersons = new ArrayList<ClientInformation>();
	private String clientId;

	public boolean isGuestPerson()
	{
		return isGuestPerson;
	}

	public void setGuestPerson(boolean guestPerson)
	{
		isGuestPerson = guestPerson;
	}

	public List<ClientInformation> getActivePersons()
	{
		return activePersons;
	}

	public void setActivePersons(List<ClientInformation> activePersons)
	{
		this.activePersons = activePersons;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}
}
