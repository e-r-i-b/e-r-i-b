package com.rssl.phizic.business.fraudMonitoring.senders.templates;

import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.business.documents.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.business.fraudMonitoring.senders.templates.builders.AnalyzeCardPaymentSystemTransferTemplateRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для шаблона бил. получателю платежа (тип analyze)
 *
 * @author khudyakov
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardPaymentSystemTransferTemplateSender extends TemplateFraudMonitoringSenderBase
{
	private IndividualTransferTemplate individualTransferTemplate;
	private PaymentSystemTransferTemplate paymentSystemTransferTemplate;
	private Type type;

	public AnalyzeCardPaymentSystemTransferTemplateSender(IndividualTransferTemplate individualTransferTemplate)
	{
		this.individualTransferTemplate = individualTransferTemplate;
		this.type = Type.IndividualTransferTemplate;
	}

	public AnalyzeCardPaymentSystemTransferTemplateSender(PaymentSystemTransferTemplate paymentSystemTransferTemplate)
	{
		this.paymentSystemTransferTemplate = paymentSystemTransferTemplate;
		this.type = Type.PaymentSystemTransferTemplate;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case IndividualTransferTemplate :
			{
				return new AnalyzeCardPaymentSystemTransferTemplateRequestBuilder<IndividualTransferTemplate>()
						.append(individualTransferTemplate)
						.build();
			}
			case PaymentSystemTransferTemplate :
			{
				return new AnalyzeCardPaymentSystemTransferTemplateRequestBuilder<PaymentSystemTransferTemplate>()
						.append(paymentSystemTransferTemplate)
						.build();
			}
			default : throw new IllegalArgumentException();
		}
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		switch (type)
		{
			case IndividualTransferTemplate :
			{
				return individualTransferTemplate;
			}
			case PaymentSystemTransferTemplate :
			{
				return paymentSystemTransferTemplate;
			}
			default : throw new IllegalArgumentException();
		}
	}

	private enum Type
	{
		IndividualTransferTemplate,
		PaymentSystemTransferTemplate
	}
}
