package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeToAccountRurPaymentDocumentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * @author vagin
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер во ФМ для перевода с карты/вклада на на счет в "нашем" банке или на счет в другой банк через платежную систему России
 */
public class AnalyzeToAccountRurPaymentDocumentSender extends DocumentFraudMonitoringSenderBase
{
	private RurPayment document;

	public AnalyzeToAccountRurPaymentDocumentSender(RurPayment document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeToAccountRurPaymentDocumentRequestBuilder()
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
