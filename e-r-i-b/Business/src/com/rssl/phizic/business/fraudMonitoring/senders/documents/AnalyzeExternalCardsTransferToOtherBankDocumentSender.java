package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeExternalCardsTransferToOtherBankDocumentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * @author vagin
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер во ФМ для перевода с карты на внешнюю карту.
 */
public class AnalyzeExternalCardsTransferToOtherBankDocumentSender extends DocumentFraudMonitoringSenderBase
{
	private RurPayment document;

	public AnalyzeExternalCardsTransferToOtherBankDocumentSender(RurPayment document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeExternalCardsTransferToOtherBankDocumentRequestBuilder()
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
