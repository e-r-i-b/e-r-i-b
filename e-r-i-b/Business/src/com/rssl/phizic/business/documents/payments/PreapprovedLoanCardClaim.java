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
	 * ���������� ��� ���������
	 */
	public Class<? extends GateDocument> getType()
	{
		return PreApprovedLoanCardClaim.class;
	}
}
