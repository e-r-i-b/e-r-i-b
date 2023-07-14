package com.rssl.phizicgate.rsretailV6r20.commission;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author //v620
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class SWIFTPaymentCommissionCalculator extends DefaultPaymentCommissionCalculator
{
	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateException
	{
		Element tagName = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "externalTransfer_q");
		XmlHelper.appendSimpleElement(tagName, "dateCreated", String.format("%1$td.%1$tm.%1$tY", gateDocument.getClientCreationDate().getTime()));

		if (!(gateDocument instanceof SWIFTPayment))
			throw new GateException("Неверный тип платежа, должен быть - SWIFTPayment.");

		SWIFTPayment CurrencyPaymentDocument = (SWIFTPayment) gateDocument;

		XmlHelper.appendSimpleElement(tagName,"chargeOffAccount",CurrencyPaymentDocument.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(tagName, "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",CurrencyPaymentDocument.getChargeOffAmount().getDecimal().toString());

		String operationType = getOperationType();
		if (operationType!=null)
		  XmlHelper.appendSimpleElement(tagName, "type", operationType);
		String subspecies = getOperationSubspecies(gateDocument);
		if (subspecies!=null)
			XmlHelper.appendSimpleElement(tagName, "subType", subspecies);

		XmlHelper.appendSimpleElement(tagName, "receiverName",CurrencyPaymentDocument.getReceiverName());
		XmlHelper.appendSimpleElement(tagName, "receiverBIC",CurrencyPaymentDocument.getReceiverSWIFT());
		//XmlHelper.appendSimpleElement(tagName, "receiverCorAccount",CurrencyPaymentDocument.getReceiverCorAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverAccount",CurrencyPaymentDocument.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverBankName",CurrencyPaymentDocument.getReceiverBankName());
		XmlHelper.appendSimpleElement(tagName, "ground",CurrencyPaymentDocument.getGround());
	}

	/*
	private static final String PARAMETER_SUBOPERATION_TYPE_SPOT_NAME = "suboperation-type-spot";
	private static final String PARAMETER_SUBOPERATION_TYPE_TOM_NAME = "suboperation-type-tom";
	private static final String PARAMETER_SUBOPERATION_TYPE_TOD_NAME = "suboperation-type-tod";

	protected String getOperationSubspecies(GateDocument doc) throws GateException
	{
		SWIFTPayment payment = (SWIFTPayment) doc;
		if (payment.getConditions() == SWIFTPaymentConditions.tod)
			return getCurrencyTransferSuboperationTOD();
		if (payment.getConditions() == SWIFTPaymentConditions.tom)
			return getCurrencyTransferSuboperationTOM();
		if (payment.getConditions() == SWIFTPaymentConditions.spot)
			return getCurrencyTransferSuboperationSPOT();
		throw new GateException("не определены условия перевода!");
	}

	private String getCurrencyTransferSuboperationSPOT()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_SPOT_NAME);
	}

	private String getCurrencyTransferSuboperationTOM()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOM_NAME);
	}

	private String getCurrencyTransferSuboperationTOD()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOD_NAME);
	}*/
}
