package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author Egorova
 * @ created 28.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodTransferSender  extends AbstractDocumentSender
{
	private static final String PARAMETER_RECEIVER_CORRACCOUNT = "receiver-corraccount";
	private static final String PARAMETER_RECEIVER_BIK = "receiver-bic";
	private static final String PARAMETER_RECEIVER_INN = "receiver-inn";
	private static final String PARAMETER_RECEIVER_BANK_NAME = "receiver-bank-name";

	public GorodTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(gateMessage, gateDocument);
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - ClientAccountsTransfer.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) gateDocument;

		gateMessage.addParameter("chargeOffAccount",payment.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(gateMessage.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",payment.getChargeOffAmount().getDecimal().toString());
		gateMessage.addParameter("receiverName",payment.getReceiverTransitBank().getName());
		gateMessage.addParameter("receiverBIC",getParameter(PARAMETER_RECEIVER_BIK));
		gateMessage.addParameter("receiverCorAccount",getParameter(PARAMETER_RECEIVER_CORRACCOUNT));
		gateMessage.addParameter("receiverINN", getParameter(PARAMETER_RECEIVER_INN));
		gateMessage.addParameter("receiverKPP","09288706");
		gateMessage.addParameter("receiverBankName",getParameter(PARAMETER_RECEIVER_BANK_NAME));
		gateMessage.addParameter("receiverAccount",payment.getReceiverTransitAccount());
		if(payment.getCommission() != null)
			gateMessage.addParameter("commission", payment.getCommission().getDecimal().toString());
		gateMessage.addParameter("ground", payment.getGround());
		if(payment.getExternalId()!= null)
		{
			String extId = payment.getExternalId();
			String key = extId.substring(extId.indexOf("key:") + 4,extId.indexOf(","));
			String kind = extId.substring(extId.indexOf("kind:")+5, extId.length() );
			gateMessage.addParameter("applKey", key);
			gateMessage.addParameter("applKind", kind);
		}
	}
}
