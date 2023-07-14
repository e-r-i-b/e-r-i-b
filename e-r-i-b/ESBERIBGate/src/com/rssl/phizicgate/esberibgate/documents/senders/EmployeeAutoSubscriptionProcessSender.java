package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionStatusModRq_Type;

/**
 * @author osminin
 * @ created 25.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Сендер отмены, приостановки, возобновления автоплатежа, созданного сотрудником
 */
public class EmployeeAutoSubscriptionProcessSender extends AutoSubscriptionProcessSender
{
	public EmployeeAutoSubscriptionProcessSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		if (!CardPaymentSystemPaymentLongOffer.class.isAssignableFrom(transfer.getType()))
			throw new GateException("Ожидается CardPaymentSystemPaymentLongOffer");

		EmployeeCardPaymentSystemPaymentLongOffer payment = (EmployeeCardPaymentSystemPaymentLongOffer) transfer;
		AutoSubscriptionStatusModRq_Type request = billingRequestHelper.createEmployeeAutoSubscriptionStatusModRq(payment);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setAutoSubscriptionStatusModRq(request);

		return ifxRq;
	}
}
