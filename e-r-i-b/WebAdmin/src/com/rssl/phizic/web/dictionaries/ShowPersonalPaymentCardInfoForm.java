package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author Gainanov
 * @ created 22.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowPersonalPaymentCardInfoForm extends FilterActionForm
{
	private Client client;
	private ActivePerson activePerson;
	private boolean validCard;
	private String cardId;
	private Boolean    modified=false;

	public String getCardId()
	{
		return cardId;
	}

	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	public boolean isValidCard()
	{
		return validCard;
	}

	public void setValidCard(boolean validCard)
	{
		this.validCard = validCard;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}
}
