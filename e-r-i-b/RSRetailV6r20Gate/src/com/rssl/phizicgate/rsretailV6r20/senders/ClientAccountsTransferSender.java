package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TarifPlanCodeType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

public class ClientAccountsTransferSender extends AbstractDocumentSender
{
	public ClientAccountsTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof ClientAccountsTransfer))
			throw new GateException("Неверный тип платежа, должен быть - ClientAccountsTransfer.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("internalTransfer_q");
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(gateMessage, gateDocument);
		if (!(gateDocument instanceof AbstractAccountTransfer))
			throw new GateException("Неверный тип платежа, должен быть - AbstractAccountTransfer.");

		AbstractAccountTransfer gatePayment = (AbstractAccountTransfer) gateDocument;

		String chargeOffAccount = gatePayment.getChargeOffAccount();
		String receiverAccount = getReceiverAccount(gatePayment);
		gateMessage.addParameter("chargeOffAccount", chargeOffAccount);

		Currency chargeOffCurrency = getAccountCurrency(chargeOffAccount);
		Currency receiverCurrency = getAccountCurrency(receiverAccount);
		appendAmount(gateMessage, "chargeOffAmount", gatePayment.getChargeOffAmount());
		if (!chargeOffCurrency.equals(receiverCurrency))
		{
			appendAmount(gateMessage, "receiverAmount", gatePayment.getDestinationAmount());
		}

		gateMessage.addParameter("receiverAccount", receiverAccount);
		if (gatePayment.getCommission() != null)
			gateMessage.addParameter("commission", gatePayment.getCommission().getDecimal().toString());

		if (gatePayment.getGround() != null)
			gateMessage.addParameter("ground", StringHelper.replaceQuotes(gatePayment.getGround()));
		if (!chargeOffCurrency.equals(receiverCurrency))
		{
			//добавляем курс
			CurrencyRateService rateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			// курс всегда такой: в ритейл уходит сумма продажи+курс. клиент всегда продает chargeOffAmount банку
			Office office = gatePayment.getOffice();

			CurrencyRate rate = rateService.convert(gatePayment.getChargeOffAmount(), receiverCurrency, office, TarifPlanCodeType.UNKNOWN);
			double rateForRetail = rate.getFromValue().doubleValue();
			// но! В ритейле происходит следуюее: если это продажа валюты или конверсия - сумма списания (та, что передается в ритейл)
			// умножается на курс. При покупке валюты сумма, переданная в ритейл, делится (!) на курс.
			// поэтому, если валюта суммы списания = руб - это покупка валюты. курс вычисляем иначе.
			if ("RUB".equals(chargeOffCurrency.getCode()))
			{
				rate = rateService.convert(receiverCurrency, gatePayment.getChargeOffAmount(), office, TarifPlanCodeType.UNKNOWN);
				rateForRetail = rate.getToValue().doubleValue();
			}
			gateMessage.addParameter("operationRate", rateForRetail);
		}
	}

	private void appendAmount(GateMessage gateMessage, String tagName, Money amount)
	{
		Element element = XmlHelper.appendSimpleElement(gateMessage.getDocument().getDocumentElement(), tagName);
		XmlHelper.appendSimpleElement(element, "value", amount.getDecimal().toString());
		String retailCurCode = "RUB".equals(amount.getCurrency().getCode()) ? "RUR" : amount.getCurrency().getCode();
		XmlHelper.appendSimpleElement(element, "currencyCode", retailCurCode);
	}

	protected String getReceiverAccount(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof ClientAccountsTransfer))
			throw new GateException("Неверный тип платежа, должен быть - ClientAccountsTransfer.");

		ClientAccountsTransfer clientAccountsTransfer = (ClientAccountsTransfer) document;
		return clientAccountsTransfer.getReceiverAccount();
	}
}
