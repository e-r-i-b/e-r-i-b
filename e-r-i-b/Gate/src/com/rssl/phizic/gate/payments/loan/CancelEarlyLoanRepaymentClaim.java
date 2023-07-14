package com.rssl.phizic.gate.payments.loan;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * отмена досрочного погашение кредита
 * @author basharin
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 */

public interface CancelEarlyLoanRepaymentClaim extends SynchronizableDocument
{
	GateDocument getTransferPayment();

	Long getTerminationRequestId();
}
