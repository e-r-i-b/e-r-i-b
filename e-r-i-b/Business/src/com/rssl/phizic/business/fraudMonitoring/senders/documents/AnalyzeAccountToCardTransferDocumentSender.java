package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeAccountToCardTransferDocumentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для перевода со счета на карту (тип analyze)
 *
 * @author khudyakov
 * @ created 09.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeAccountToCardTransferDocumentSender extends DocumentFraudMonitoringSenderBase
{
	private InternalTransfer document;

	public AnalyzeAccountToCardTransferDocumentSender(InternalTransfer document)
	{
		this.document = document;
	}

	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeAccountToCardTransferDocumentRequestBuilder()
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
