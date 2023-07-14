package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.CreateAutoPaymentImpl;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCreateCardPaymentSystemPaymentAutoPaymentRequestBuilder;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeCreateCardPaymentSystemPaymentAutoSubscriptionRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для заявки на создание биллингового автоплатежа (тип analyze)
 *
 * @author khudyakov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateCardPaymentSystemPaymentLongOfferSender extends DocumentFraudMonitoringSenderBase
{
	private JurPayment jurPayment;
	private CreateAutoPaymentImpl autoPayment;
	private Type type;

	public AnalyzeCreateCardPaymentSystemPaymentLongOfferSender(JurPayment jurPayment)
	{
		this.jurPayment = jurPayment;
		this.type = Type.JurPayment;
	}

	public AnalyzeCreateCardPaymentSystemPaymentLongOfferSender(CreateAutoPaymentImpl autoPayment)
	{
		this.autoPayment = autoPayment;
		this.type = Type.CreateAutoPayment;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case JurPayment :
			{
				return new AnalyzeCreateCardPaymentSystemPaymentAutoSubscriptionRequestBuilder()
						.append(jurPayment)
						.append(getInitializationData())
						.build();
			}
			case CreateAutoPayment :
				return new AnalyzeCreateCardPaymentSystemPaymentAutoPaymentRequestBuilder()
						.append(autoPayment)
						.append(getInitializationData())
						.build();
			default : throw new IllegalArgumentException();
		}
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
			case CreateAutoPayment :
			{
				return autoPayment;
			}
			default : throw new IllegalArgumentException();
		}
	}

	private enum Type
	{
		JurPayment,
		CreateAutoPayment;
	}
}
