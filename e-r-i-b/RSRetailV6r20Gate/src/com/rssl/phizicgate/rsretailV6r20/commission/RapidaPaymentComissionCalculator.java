package com.rssl.phizicgate.rsretailV6r20.commission;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author Krenev
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class RapidaPaymentComissionCalculator extends DefaultPaymentCommissionCalculator
{
	private static final String PARAMETER_RECEIVER_ACCOUNT = "receiver-account";
	private static final String PARAMETER_OPERATION_TYPE = "retail-operation-type";
	private static final String PARAMETER_SUBOPERATION_TYPE = "retail-suboperation-type";
	private static final String PARAMETER_RECEIVER_CORRACCOUNT = "receiver-corraccount";
	private static final String PARAMETER_RECEIVER_BIK = "receiver-bic";
	private static final String PARAMETER_RECEIVER_NAME = "receiver-name";
	private static final String PARAMETER_RECEIVER_BANK_NAME = "receiver-bank-name";

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateException
	{
		Element tagName = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "externalTransfer_q");
		XmlHelper.appendSimpleElement(tagName, "dateCreated", String.format("%1$td.%1$tm.%1$tY", gateDocument.getClientCreationDate().getTime()));

		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment RapidaPaymentDocument = (AccountPaymentSystemPayment) gateDocument;

		XmlHelper.appendSimpleElement(tagName,"chargeOffAccount",RapidaPaymentDocument.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(tagName, "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",RapidaPaymentDocument.getChargeOffAmount().getDecimal().toString());

		XmlHelper.appendSimpleElement(tagName,"receiverAccount",getParameter(PARAMETER_RECEIVER_ACCOUNT).toString());
		XmlHelper.appendSimpleElement(tagName,"receiverCorAccount",getParameter(PARAMETER_RECEIVER_CORRACCOUNT).toString());
		XmlHelper.appendSimpleElement(tagName,"receiverBIC",getParameter(PARAMETER_RECEIVER_BIK).toString());
		XmlHelper.appendSimpleElement(tagName,"receiverName",getParameter(PARAMETER_RECEIVER_NAME).toString());
		XmlHelper.appendSimpleElement(tagName,"receiverBankName",getParameter(PARAMETER_RECEIVER_BANK_NAME).toString());
		//XmlHelper.appendSimpleElement(tagName,"type",getParameter(PARAMETER_OPERATION_TYPE));
		//XmlHelper.appendSimpleElement(tagName,"type",67);
		XmlHelper.appendSimpleElement(tagName,"subType",getParameter(PARAMETER_SUBOPERATION_TYPE).toString());
		XmlHelper.appendSimpleElement(tagName,"ground",RapidaPaymentDocument.getGround());
	}
}
