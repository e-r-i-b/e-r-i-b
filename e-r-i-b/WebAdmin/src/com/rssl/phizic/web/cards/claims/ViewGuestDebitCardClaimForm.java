package com.rssl.phizic.web.cards.claims;

import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * ‘орма дл€ просмотра за€вки гост€ на дебетовые карты
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewGuestDebitCardClaimForm extends EditFormBase
{
	private IssueCardDocumentImpl claim;

	/**
	 * @return за€вку гост€
	 */
	public IssueCardDocumentImpl getClaim()
	{
		return claim;
	}

	/**
	 * «адать за€вку гост€
	 * @param claim - за€вка
	 */
	public void setClaim(IssueCardDocumentImpl claim)
	{
		this.claim = claim;
	}
}
