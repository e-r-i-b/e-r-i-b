package com.rssl.phizicgate.rsretailV6r20.commission;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author Krenev
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */
//перевод на счёт в другой банк
public class RUSPaymentCommissionCalculator extends DefaultPaymentCommissionCalculator
{
	private static final String PARAMETER_SUBOPERATION_TYPE_KORR = "suboperation-type-korr";
	private static final String PARAMETER_SUBOPERATION_TYPE_JUR = "suboperation-type-transfer-jur";
	private static final String PARAMETER_SUBOPERATION_TYPE_FIZ = "suboperation-type-transfer-fiz";
	private static final String PARAMETR_SUBOPERATION_TYPE_TAX = "suboperation-tax-type";

	protected String getOperationSubspecies(GateDocument doc) throws GateException
	{
		if(!(doc instanceof AbstractRUSPayment))
		{
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		}
		AbstractRUSPayment rusPayment = (AbstractRUSPayment)doc;
		String res = "0";

		if (rusPayment instanceof AbstractJurTransfer)
		{
			res = getTransferSuboperationJUR();
		}
		else
		{
		    res = getTransferSuboperationFIZ();
		}

		if (PaymentHelper.isCorrespondentBank(rusPayment))
		{
			res = getTransferSuboperationKORR();
		}
		return res;
	}
	private String getTransferSuboperationKORR()
	{
		return (String) getParameter(RUSPaymentCommissionCalculator.PARAMETER_SUBOPERATION_TYPE_KORR);
	}
	private String getTransferSuboperationJUR()
	{
		return (String) getParameter(RUSPaymentCommissionCalculator.PARAMETER_SUBOPERATION_TYPE_JUR);
	}
	private String getTransferSuboperationFIZ()
	{
		return (String) getParameter(RUSPaymentCommissionCalculator.PARAMETER_SUBOPERATION_TYPE_FIZ);
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateException
	{
		Element tagName = XmlHelper.appendSimpleElement(gateMessage.getDocument().getDocumentElement(), "externalTransfer_q");
		XmlHelper.appendSimpleElement(tagName, "dateCreated", String.format("%1$td.%1$tm.%1$tY", gateDocument.getClientCreationDate().getTime()));

		if (!(gateDocument instanceof AbstractRUSPayment))
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		
		AccountRUSPayment rusPayment = (AccountRUSPayment) gateDocument;

		XmlHelper.appendSimpleElement(tagName, "chargeOffAccount", rusPayment.getChargeOffAccount());
		String operationType = getOperationType();
		if(operationType!=null)
			XmlHelper.appendSimpleElement(tagName, "type", operationType);
		String subspecies = getOperationSubspecies(gateDocument);
		if(subspecies!=null)
			XmlHelper.appendSimpleElement(tagName, "subType", subspecies);
		Element element = XmlHelper.appendSimpleElement(tagName, "chargeOffAmount");
		XmlHelper.appendSimpleElement(element, "value", rusPayment.getChargeOffAmount().getDecimal().toString());
		XmlHelper.appendSimpleElement(tagName, "receiverAccount", rusPayment.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverName", rusPayment.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverBIC", rusPayment.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverCorAccount", rusPayment.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverINN", rusPayment.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverKPP", rusPayment.getReceiverAccount());
		XmlHelper.appendSimpleElement(tagName, "receiverBankName", rusPayment.getReceiverAccount());
	}
}
