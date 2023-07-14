package com.rssl.phizic.business.ips;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 04.08.2011
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
		return card.hashCode();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof IPSCardOperationClaim))
			return false;

		IPSCardOperationClaim that = (IPSCardOperationClaim) o;

		if (!client.getInternalOwnerId().equals(that.getClient().getInternalOwnerId()))
			return false;
		if (!card.getNumber().equals(that.getCard().getNumber()))
			return false;

		return true;
	}

	public String toString()
	{
		return "IPSCardOperationClaimImpl{" +
				"login=" + client.getInternalOwnerId() +
				", card=" + MaskUtil.getCutCardNumberForLog(card.getNumber()) +
				", startDate=" + DateHelper.toISO8601DateFormat(startDate) +
				'}';
	}
}
