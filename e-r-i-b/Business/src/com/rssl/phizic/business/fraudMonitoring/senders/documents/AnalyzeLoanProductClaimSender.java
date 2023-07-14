package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.LoanProductClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeLoanProductClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * @author tisov
 * @ created 18.05.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeLoanProductClaimSender extends DocumentFraudMonitoringSenderBase
{
	private LoanProductClaim document;         //заявка на кредит

	public AnalyzeLoanProductClaimSender(LoanProductClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeLoanProductClaimRequestBuilder()
				.append(document)
				.append(getInitializationData())
				.build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return document;
	}
}
