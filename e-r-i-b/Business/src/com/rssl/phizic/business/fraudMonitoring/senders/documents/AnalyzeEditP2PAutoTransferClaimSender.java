package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.EditP2PAutoTransferClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeEditP2PAutoTransferClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для всех заявок редактирования автоперевода в Автопай (тип analyze)
 *
 * @author khudyakov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditP2PAutoTransferClaimSender extends DocumentFraudMonitoringSenderBase
{
	private EditP2PAutoTransferClaim document;

	public AnalyzeEditP2PAutoTransferClaimSender(EditP2PAutoTransferClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeEditP2PAutoTransferClaimRequestBuilder()
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
