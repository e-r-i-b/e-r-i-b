package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

public class RUSPaymentSender extends AbstractDocumentSender
{
	public RUSPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AbstractRUSPayment))
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof AbstractRUSPayment))
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		AccountRUSPayment rusPayment = (AccountRUSPayment) gateDocument;

		message.addParameter("chargeOffAccount",rusPayment.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",rusPayment.getChargeOffAmount().getDecimal().toString());

		message.addParameter("receiverName",        PaymentHelper.getReceiverName(rusPayment));
		ResidentBank residentBank = rusPayment.getReceiverBank();
		message.addParameter("receiverBIC",         residentBank.getBIC());
		message.addParameter("receiverCorAccount",  residentBank.getAccount());
		if (!StringHelper.isEmpty(rusPayment.getReceiverINN()))
			message.addParameter("receiverINN",     rusPayment.getReceiverINN());
		if (rusPayment instanceof AbstractJurTransfer)
		{
			AbstractJurTransfer jurPayment = (AbstractJurTransfer) rusPayment;
			message.addParameter("receiverKPP",     jurPayment.getReceiverKPP());
		}
		message.addParameter("receiverBankName",    residentBank.getName());
		message.addParameter("receiverAccount",     rusPayment.getReceiverAccount());
		message.addParameter("ground",              rusPayment.getGround());
		if (rusPayment.getCommission() != null)
			message.addParameter("commission", rusPayment.getCommission().getDecimal().toString());
	}
}