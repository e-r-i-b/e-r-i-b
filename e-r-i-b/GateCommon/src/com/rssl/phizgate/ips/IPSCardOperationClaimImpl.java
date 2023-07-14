package com.rssl.phizgate.ips;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizgate.mobilebank.GateCardHelper;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 01.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class IPSCardOperationClaimImpl implements IPSCardOperationClaim
{
	private Client client;

	private Card card;

	private Calendar startDate;

	///////////////////////////////////////////////////////////////////////////

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public int hashCode()
	{
		return card.getNumber().hashCode();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		IPSCardOperationClaim that = (IPSCardOperationClaim) o;

		if (!card.getNumber().equals(that.getCard().getNumber()))
			return false;
		if (!client.getInternalOwnerId().equals(that.getClient().getInternalOwnerId()))
			return false;

		return true;
	}

	public String toString()
	{
		return "IPSCardOperationClaimImpl{" +
				"login=" + client.getInternalOwnerId() +
				", card=" + GateCardHelper.hideCardNumber(card.getNumber()) +
				", startDate=" + DateHelper.toISO8601DateFormat(startDate) +
				'}';
	}
}
