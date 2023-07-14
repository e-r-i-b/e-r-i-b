package com.rssl.phizic.gorod;

import com.rssl.phizic.gate.payments.systems.gorod.GorodCard;
import com.rssl.phizic.gate.clients.Client;

/**
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodCardImpl implements GorodCard
{
	private Client cardOwner;
	private String cardId;
	public Client getOwner()
	{
		return cardOwner;
	}

	public String getId()
	{
		return cardId;
	}

	public void setId(String cardId)
	{
		this.cardId = cardId;
	}

	public void setOwner(Client cardOwner)
	{
		this.cardOwner = cardOwner;
	}
}

