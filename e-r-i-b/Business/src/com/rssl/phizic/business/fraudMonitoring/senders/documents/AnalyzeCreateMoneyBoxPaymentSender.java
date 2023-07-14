package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCreateMoneyBoxPaymentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для всех заявок создания копилки в Автопай (тип analyze)
 *
 * @author khudyakov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateMoneyBoxPaymentSender extends DocumentFraudMonitoringSenderBase
{
	private CreateMoneyBoxPayment document;

	public AnalyzeCreateMoneyBoxPaymentSender(CreateMoneyBoxPayment document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeCreateMoneyBoxPaymentRequestBuilder()
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
