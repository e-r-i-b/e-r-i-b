package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.ReIssueCardClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeReIssueCardClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для заявки перевыпуска карты (тип analyze)
 *
 * @author khudyakov
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeReIssueCardClaimSender extends DocumentFraudMonitoringSenderBase
{
	private ReIssueCardClaim document;

	public AnalyzeReIssueCardClaimSender(ReIssueCardClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeReIssueCardClaimRequestBuilder()
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
