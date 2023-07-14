package com.rssl.phizicgate.rsretailV6r4.commission;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;

public class DefaultPaymentCommissionCalculator extends AbstractCommissionCalculator
{
	private static final String PARAMETER_OPERATION_TYPE_NAME = "operation-type";
	private static final String PARAMETER_SUBOPERATION_TYPE_NAME = "suboperation-type";

	/**
	 * получить комиссию по документу
	 * @param document документ
	 */
	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade webBankServiceFacade = getWebBankServiceFacade();

		Money amount = getChargeOffAmount(document);

		GateMessage message = webBankServiceFacade.createRequest("commissionPay_q");

		fillGateMessage(message,document);

		Document response = webBankServiceFacade.sendOnlineMessage(message, null);

		BigDecimal commission = getDocumentCommission(response);
		document.setCommission(new Money(commission, amount.getCurrency()));
	}

	/**
	 * получить тип операции.
	 * @return тип операции.
	 */
	protected String getOperationType(){
		return (String) getParameter(DefaultPaymentCommissionCalculator.PARAMETER_OPERATION_TYPE_NAME);
	}

	protected String getOperationSubspecies(GateDocument doc) throws GateException{
		return (String) getParameter(DefaultPaymentCommissionCalculator.PARAMETER_SUBOPERATION_TYPE_NAME);
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateException
	{
		Element tagName = XmlHelper.appendSimpleElement(gateMessage.getDocument().getDocumentElement(), "internalTransfer_q");
		XmlHelper.appendSimpleElement(tagName, "dateCreated", String.format("%1$td.%1$tm.%1$tY", gateDocument.getClientCreationDate().getTime()));

		//TODO в случае с заявкой на открытие счета крашится
//		if (!(gateDocument instanceof ClientAccountsTransfer))
//			throw new GateException("Неверный тип платежа, должен быть - ClientAccountsTransfer.");

		XmlHelper.appendSimpleElement(tagName, "chargeOffAccount", getChargeOffAccount(gateDocument));
		String operationType = getOperationType();
		if(operationType!=null)
			XmlHelper.appendSimpleElement(tagName, "type", operationType);
		String subspecies = getOperationSubspecies(gateDocument);
		if(subspecies!=null)
			XmlHelper.appendSimpleElement(tagName, "subType", subspecies);
		Element element = XmlHelper.appendSimpleElement(tagName, "chargeOffAmount");
		XmlHelper.appendSimpleElement(element, "value", getChargeOffAmount(gateDocument).getDecimal().toString());
		XmlHelper.appendSimpleElement(tagName, "receiverAccount", getReceiverAccount(gateDocument));
	}

	/**
	 *
	 * @return сумму списания
	 */
	public Money getChargeOffAmount(GateDocument document)
	{
	    return ((AbstractTransfer) document).getChargeOffAmount();
	}

	public String getChargeOffAccount(GateDocument document)
	{
		return ((ClientAccountsTransfer) document).getChargeOffAccount();
	}

	public String getReceiverAccount(GateDocument document)
	{
		return ((ClientAccountsTransfer) document).getReceiverAccount();
	}
}