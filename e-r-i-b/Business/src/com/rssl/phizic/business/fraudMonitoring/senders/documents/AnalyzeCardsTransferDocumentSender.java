package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCardsTransferDocumentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для перевода между моими картами (тип analyze)
 *
 * @author khudyakov
 * @ created 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardsTransferDocumentSender extends DocumentFraudMonitoringSenderBase
{
	private InternalTransfer document;

	public AnalyzeCardsTransferDocumentSender(InternalTransfer document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeCardsTransferDocumentRequestBuilder()
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