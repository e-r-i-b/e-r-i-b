package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author krenev
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 * Биллинговый сендер для Московского банка. отдельно обрабатывается случай связки с ЦПФЛ.
 */
public class BillingPaymentSenderMoscow extends GorodCODSender
{
	public BillingPaymentSenderMoscow(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * заполнить сообщение гейту данными из документа
	 *
	 * @param gateMessage сообщение
	 * @param gateDocument документ
	 */
	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) gateDocument;
		Document nplatRequisites = CPFLBillingPaymentHelper.getNplatRequisites(payment);
		     
		// для не ЦПФЛ работаем по прежней схеме. те через executeBillingPayment
		//критерий - отсутствие скрытого доп поля nplatRequisites
		if(nplatRequisites == null)
		{
			super.fillGateMessage(gateMessage, gateDocument);
			return;
		}

		gateMessage.addParameter("clientId", "0");
		//если интегрируемся с ЦПФЛ работаем устанавливаем идентфикатор поставщика из ЦПФЛ.
		//по спецификации длина должна быть 9.
		//в примерах обмена сообщениями из банка к recipientList_a/uniqueNumber добавляются нули сначала
		if (CPFLBillingPaymentHelper.isContractual(payment.getReceiverPointCode()))
		{
			gateMessage.addParameter("uniqueNumber", StringHelper.appendLeadingZeros(payment.getReceiverPointCode(), 9));
		}
		gateMessage.addParameter("account", payment.getReceiverAccount());
		ResidentBank bank = payment.getReceiverBank();
		gateMessage.addParameter("bankBIC", bank.getBIC());
		if (!StringHelper.isEmpty(bank.getAccount()))
		{
			gateMessage.addParameter("bankAccount", bank.getAccount());
		}
		gateMessage.addParameter("inn", payment.getReceiverINN());

		gateMessage.addParameter("sum", payment.getDestinationAmount().getDecimal().toString());
		gateMessage.addParameter("commision", payment.getCommission().getDecimal().toString());
		gateMessage.addParameter("debitAccount", payment.getChargeOffAccount());
		//если интегрируемся с ЦПФЛ, то назначение платежа берем из nplatRequisites\Requisites .
		String ground = XmlHelper.getSimpleElementValue(nplatRequisites.getDocumentElement(), "requisites");
		gateMessage.addParameter("purpose", ground);
		gateMessage.addParameter("beneficiary", payment.getReceiverName());
		//если интегрируемся с ЦПФЛ, то добавляем секцию nplatRequisites .
		Document document = gateMessage.getDocument();
		Element root = document.getDocumentElement();
		root.appendChild(document.importNode(nplatRequisites.getDocumentElement(), true));
		String usePaymentOrder = ConfigFactory.getConfig(SpecificGateConfig.class).getUsePaymentOrderForAccountPaymentSystemPayment();
		if (usePaymentOrder != null)
		{
			gateMessage.addParameter("usePaymentOrder", usePaymentOrder);
		}
		String operationCode = payment.getOperationCode();
		if (!StringHelper.isEmpty(operationCode))
		{
			//Передаем только 5 цифр, без {VO }
			XmlHelper.appendSimpleElement(root, "operationCode", operationCode.substring(3, operationCode.length()-1));
		}
		gateMessage.addParameter("clientNumber", payment.getDocumentNumber());
		gateMessage.addParameter("clientDate", XMLDatatypeHelper.formatDateWithoutTimeZone(payment.getClientCreationDate()));		
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			 throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		Document nplatRequisites = CPFLBillingPaymentHelper.getNplatRequisites((AccountPaymentSystemPayment)gateDocument);

		return (nplatRequisites == null) ? super.getRequestName(gateDocument) : "executePayment_q";
	}
}
