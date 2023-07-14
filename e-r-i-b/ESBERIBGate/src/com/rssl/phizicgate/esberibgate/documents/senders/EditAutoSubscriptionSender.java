package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardPaymentSystemPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionModRq_Type;

/**
 * @author niculichev
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditAutoSubscriptionSender extends BillingPaymentDocumentAutoSubSender
{

	public EditAutoSubscriptionSender(GateFactory factory)
	{
		super(factory);
	}

	protected AutoSubscriptionModRq_Type createAutoSubscriptionModRq(CardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateLogicException, GateException
	{
		if (payment.getType() != EditCardPaymentSystemPaymentLongOffer.class)
			throw new GateException("Ожидается EditCardPaymentSystemPaymentLongOffer");

		return billingRequestHelper.createEditAutoSubscriptionModRq((EditCardPaymentSystemPaymentLongOffer) payment, owner, card);
	}

	public void prepare(GateDocument transfer) throws GateException, GateLogicException
	{
		// подготовка платежа не нужна
	}
}
