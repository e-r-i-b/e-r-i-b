package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.EditAutoPaymentImpl;
import com.rssl.phizic.business.documents.payments.EditAutoSubscriptionPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeEditCardPaymentSystemPaymentAutoPaymentRequestBuilder;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeEditCardPaymentSystemPaymentAutoSubscriptionRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для заявки на редактирование биллингового автоплатежа (тип analyze)
 *
 * @author khudyakov
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditCardPaymentSystemPaymentLongOfferSender extends DocumentFraudMonitoringSenderBase
{
	private EditAutoSubscriptionPayment autoSubscription;
	private EditAutoPaymentImpl autoPayment;
	private Type type;

	public AnalyzeEditCardPaymentSystemPaymentLongOfferSender(EditAutoSubscriptionPayment autoSubscription)
	{
		this.autoSubscription = autoSubscription;
		this.type = Type.EditAutoSubscriptionPayment;
	}

	public AnalyzeEditCardPaymentSystemPaymentLongOfferSender(EditAutoPaymentImpl autoPayment)
	{
		this.autoPayment = autoPayment;
		this.type = Type.EditAutoPaymentImpl;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case EditAutoSubscriptionPayment :
			{
				return new AnalyzeEditCardPaymentSystemPaymentAutoSubscriptionRequestBuilder()
						.append(autoSubscription)
						.append(getInitializationData())
						.build();
			}
			case EditAutoPaymentImpl :
			{
				return new AnalyzeEditCardPaymentSystemPaymentAutoPaymentRequestBuilder()
						.append(autoPayment)
						.append(getInitializationData())
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
			case EditAutoSubscriptionPayment :
			{
				return autoSubscription;
			}
			case EditAutoPaymentImpl :
			{
				return autoPayment;
			}
			default : throw new IllegalArgumentException();
		}
	}

	private enum Type
	{
		EditAutoSubscriptionPayment,
		EditAutoPaymentImpl;
	}
}
