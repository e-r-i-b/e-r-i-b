package com.rssl.phizicgate.rsretailV6r4.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.*;
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
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",getAmount(rusPayment).getDecimal().toString());

		if (rusPayment instanceof AbstractJurTransfer)
		{
			AbstractJurTransfer jurTransfer = (AbstractJurTransfer) rusPayment;
			message.addParameter("receiverName",    StringHelper.replaceQuotes(jurTransfer.getReceiverName()));
		}
		else
		{
			AbstractPhizTransfer phizPayment = (AbstractPhizTransfer) rusPayment;
			message.addParameter("receiverName",    phizPayment.getReceiverSurName() + " " + phizPayment.getReceiverFirstName() + " " + phizPayment.getReceiverPatrName());
		}

		ResidentBank residentBank = rusPayment.getReceiverBank();
		message.addParameter("receiverBIC",residentBank.getBIC());
		if (!StringHelper.isEmpty(residentBank.getAccount()))
			message.addParameter("receiverCorAccount",residentBank.getAccount());
		if (!StringHelper.isEmpty(rusPayment.getReceiverINN()))
			message.addParameter("receiverINN",rusPayment.getReceiverINN());
		if (rusPayment instanceof AbstractJurTransfer)
		{
			message.addParameter("receiverKPP",((AbstractJurTransfer) rusPayment).getReceiverKPP());
		}
		message.addParameter("receiverBankName",StringHelper.replaceQuotes(residentBank.getName()));
		message.addParameter("receiverAccount",rusPayment.getReceiverAccount());
		message.addParameter("ground", StringHelper.replaceQuotes(rusPayment.getGround()));
		if (rusPayment.getCommission() != null)
			message.addParameter("commission", rusPayment.getCommission().getDecimal().toString());
	}

	protected Money getAmount(AccountRUSPayment payment)
	{
		return payment.getChargeOffAmount();
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractRUSPayment)){
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		}
		if (!(document instanceof AbstractAccountTransfer)){
			throw new GateException("Неверный тип платежа, должен быть - AbstractAccountTransfer.");
		}
		Currency chargeOffcurrency= getAccountCurrency(((AbstractAccountTransfer)document).getChargeOffAccount());
		Currency receiverAccountCurrency= getAccountCurrency(((AbstractRUSPayment)document).getReceiverAccount());
		if (!chargeOffcurrency.compare(receiverAccountCurrency))
		{
			throw new GateLogicException("Вы не можете совершить перевод в разных валютах. Выберите счета в одной валюте");
		}
		super.validate(document);
	}
}