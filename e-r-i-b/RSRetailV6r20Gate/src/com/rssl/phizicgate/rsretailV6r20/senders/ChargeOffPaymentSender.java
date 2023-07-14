package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.ChargeOffPayment;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author: Pakhomova
 * @created: 21.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffPaymentSender extends AbstractDocumentSender
{
	public ChargeOffPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected GateMessage createGateMessage(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof ChargeOffPayment))
			throw new GateException("Неверный тип платежа, должен быть - ChargeOffPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("serviceFeePayment_q");
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(gateMessage, gateDocument);
		if (!(gateDocument instanceof ChargeOffPayment))
			throw new GateException("Неверный тип платежа, должен быть - ChargeOffPayment.");


		ChargeOffPayment payment = (ChargeOffPayment) gateDocument;
		gateMessage.addParameter("chargeOffAccount", payment.getChargeOffAccount());
		gateMessage.addParameter("ground", payment.getGround());

		//gateMessage.addParameter("type", );
		//gateMessage.addParameter("subType", );

		Element chargeOffAmountElem = XmlHelper.appendSimpleElement(gateMessage.getDocument().getDocumentElement(), "chargeOffAmount");

		String retailCurCode = "RUB".equals(payment.getChargeOffAmount().getCurrency().getCode()) ? "RUR" : payment.getChargeOffAmount().getCurrency().getCode();

		XmlHelper.appendSimpleElement(chargeOffAmountElem, "value", payment.getChargeOffAmount().getDecimal().toString());
		XmlHelper.appendSimpleElement(chargeOffAmountElem, "currencyCode", retailCurCode);

	}

}
