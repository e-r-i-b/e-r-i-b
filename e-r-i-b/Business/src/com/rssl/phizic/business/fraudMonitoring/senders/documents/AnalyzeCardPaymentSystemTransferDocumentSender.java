package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCardPaymentSystemTransferJurPaymentRequestBuilder;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCardPaymentSystemTransferRurPaymentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для перевода с карты по ппроизвольным реквизитам (тип analyze)
 *
 * @author khudyakov
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardPaymentSystemTransferDocumentSender extends DocumentFraudMonitoringSenderBase
{
	private JurPayment jurPayment;
	private RurPayment rurPayment;
	private Type type;

	public AnalyzeCardPaymentSystemTransferDocumentSender(JurPayment jurPayment)
	{
		this.jurPayment = jurPayment;
		this.type = Type.JurPayment;
	}

	public AnalyzeCardPaymentSystemTransferDocumentSender(RurPayment rurPayment)
	{
		this.rurPayment = rurPayment;
		this.type = Type.RurPayment;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case JurPayment :
			{
				return new AnalyzeCardPaymentSystemTransferJurPaymentRequestBuilder()
						.append(jurPayment)
						.append(getInitializationData())
						.build();
			}
			case RurPayment :
			{
				return new AnalyzeCardPaymentSystemTransferRurPaymentRequestBuilder()
					.append(rurPayment)
					.append(getInitializationData())
					.build();
			}
		}
		throw new IllegalStateException();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		switch (type)
		{
			case JurPayment :
			{
				return jurPayment;
			}
			case RurPayment :
			{
				return rurPayment;
			}
			default : throw new IllegalArgumentException();
		}
	}

	private enum Type
	{
		JurPayment,
		RurPayment
	}
}
