package com.rssl.phizicgate.ips;

import com.rssl.phizic.gate.ips.IPSCardOperationClaim;

import java.util.*;

/**
 * @author Erkin
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */
class ClaimPack
{
	private final Calendar startDate;

	/**
	 * Заявки по номеру карты
	 */
	private final Map<String, IPSCardOperationClaim> claims = new HashMap<String, IPSCardOperationClaim>();

	///////////////////////////////////////////////////////////////////////////

	ClaimPack(Calendar startDate)
	{
		this.startDate = startDate;
	}

	Calendar getStartDate()
	{
		return startDate;
	}

	int size()
	{
		return claims.size();
	}

	Collection<IPSCardOperationClaim> getClaims()
	{
		return Collections.unmodifiableCollection(claims.values());
	}

	void addClaim(IPSCardOperationClaim claim)
	{
		claims.put(claim.getCard().getNumber(), claim);
	}

	Collection<String> getCardNumbers()
	{
		return claims.keySet();
	}

	IPSCardOperationClaim getClaimByCard(String cardNumber)
	{
		return claims.get(cardNumber);
	}
}
