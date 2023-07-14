package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.gate.payments.UtilityPayment;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author //v620
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class UtilityPaymentSender extends AbstractDocumentSender
{
	public UtilityPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	//НИЧЕГО НЕ ИСПОЛЬЗУЕТСЯ
	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof UtilityPayment))
			throw new GateException("Неверный тип платежа, должен быть - UtilityPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof UtilityPayment))
			throw new GateException("Неверный тип платежа, должен быть - UtilityPayment.");

		UtilityPayment UtilityPaymentDocument = (UtilityPayment) gateDocument;

		message.addParameter("chargeOffAccount",UtilityPaymentDocument.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",UtilityPaymentDocument.getChargeOffAmount().getDecimal().toString());

		//message.addParameter("receiverName",UtilityPaymentDocument.getReceiverName());
		//message.addParameter("receiverBIC",UtilityPaymentDocument.getReceiverSWIFT());
		//message.addParameter("receiverCorAccount",UtilityPaymentDocument.getReceiverCorAccount());
		//message.addParameter("receiverAccount",UtilityPaymentDocument.getReceiverAccount());
		//message.addParameter("receiverBankName",UtilityPaymentDocument.getReceiverBankName());
	}
}