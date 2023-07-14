package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.longoffer.EmployeeCardPaymentSystemPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionModRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;

/**
 * @author niculichev
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class BillingPaymentDocumentEmployeeAutoSubSender extends BillingPaymentDocumentAutoSubSender
{
	public BillingPaymentDocumentEmployeeAutoSubSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		if (transfer.getType() != EmployeeCardPaymentSystemPaymentLongOffer.class)
			throw new GateException("Ожидается EmployeeCardPaymentSystemPaymentLongOffer");

		EmployeeCardPaymentSystemPaymentLongOffer longOffer = (EmployeeCardPaymentSystemPaymentLongOffer) transfer;

		Client owner = getBusinessOwner(longOffer);
		Card card = getCard(owner, longOffer.getChargeOffCard(), longOffer.getOffice());

		AutoSubscriptionModRq_Type request = billingRequestHelper.createEmployeeAutoSubscriptionModRq(longOffer, owner, card);
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setAutoSubscriptionModRq(request);

		return ifxRq;
	}
}
