package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.CreateP2PAutoTransferClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCreateP2PAutoTransferClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для всех заявок создания автоперевода в Автопай (тип analyze)
 *
 * @author khudyakov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateP2PAutoTransferClaimSender extends DocumentFraudMonitoringSenderBase
{
	private CreateP2PAutoTransferClaim document;

	public AnalyzeCreateP2PAutoTransferClaimSender(CreateP2PAutoTransferClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeCreateP2PAutoTransferClaimRequestBuilder()
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
