package com.rssl.phizic.business.documents;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.loan.CancelEarlyLoanRepaymentClaim;

/**
 * отмена досрочного погашение кредита
 * @author basharin
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 */

public class CancelEarlyLoanRepaymentClaimImpl extends RecallDocument implements CancelEarlyLoanRepaymentClaim
{
	private static final String TERMINATION_REQUEST_ID = "termination-request-id";

	public Long getTerminationRequestId()
	{
		return getNullSaveAttributeLongValue(TERMINATION_REQUEST_ID);
	}

	public Class<? extends GateDocument> getType()
	{
		return CancelEarlyLoanRepaymentClaimImpl.class;
	}
}
