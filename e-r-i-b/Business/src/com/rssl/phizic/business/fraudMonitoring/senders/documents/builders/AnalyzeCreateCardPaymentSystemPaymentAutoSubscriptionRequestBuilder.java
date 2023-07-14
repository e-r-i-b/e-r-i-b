package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.ServingSystem;

/**
 * Билдер запросов для заявок создания автоплатежей с карт в БС через шину
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateCardPaymentSystemPaymentAutoSubscriptionRequestBuilder extends AnalyzeCardPaymentSystemPaymentLongOfferRequestBuilderBase<JurPayment>
{
	private JurPayment document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(JurPayment document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected JurPayment getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ServingSystem getServingSystem()
	{
		return ServingSystem.AUTOPAY;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.CREATE_AUTO_PAYMENT;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList());

		return builder.build();
	}
}
