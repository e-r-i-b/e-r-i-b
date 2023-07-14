package com.rssl.phizicgate.esberibgate.documents.senders.EarlyLoanRepaymentClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.loan.CancelEarlyLoanRepaymentClaim;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RepaymentRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RequestPrivateEarlyRepaymentRq;

/**
 * конструктор запросов на досрочное погашение кредитов
 * @author basharin
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 */

public class CancelEarlyLoanRepaymentClaimProcessor extends EarlyLoanRepaymentClaimProcessor
{
	private final CancelEarlyLoanRepaymentClaim cancelDocument;
	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 */
	public CancelEarlyLoanRepaymentClaimProcessor(GateFactory factory, CancelEarlyLoanRepaymentClaim cancelDocument, EarlyLoanRepaymentClaim document) throws GateException
	{
		super(factory, document);
		this.cancelDocument = cancelDocument;
	}

	@Override
	protected RequestPrivateEarlyRepaymentRq buildRequestObject(EarlyLoanRepaymentClaim document) throws GateException, GateLogicException
	{
		RequestPrivateEarlyRepaymentRq earlyRepaymentRq = super.buildRequestObject(document);
		earlyRepaymentRq.setOperUID(OperationContext.getCurrentOperUID());
		earlyRepaymentRq.setRqUID(cancelDocument.getExternalId());
		return earlyRepaymentRq;
	}

	@Override
	protected RepaymentRequestType makeRepaymentRequest(EarlyLoanRepaymentClaim document)
	{
		RepaymentRequestType repaymentRequest = super.makeRepaymentRequest(document);
		repaymentRequest.setTerminationRequestId(cancelDocument.getTerminationRequestId());
		return repaymentRequest;
	}
}
