package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.claims.cards.PreApprovedLoanCardClaim;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * @author Jatsky
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */

@Deprecated
public class PreapprovedLoanCardClaim extends LoanCardOfferClaim
{
	/**
	 * Фактичский тип документа
	 */
	public Class<? extends GateDocument> getType()
	{
		return PreApprovedLoanCardClaim.class;
	}
}
