package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizgate.common.payments.AbstractCommissionCalculator;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.math.BigDecimal;

/**
 * Коммишн калькулятор для перевода c карты на карту
 * @author niculichev
 * @ created 17.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class CardsTransferCommissionCalculator extends AbstractCommissionCalculator
{
	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		if (!CardsTransfer.class.isAssignableFrom(document.getType()))
			throw new GateException("Неверный тип платежа - ожидается ExternalCardsTransferToOtherBank");

		CardsTransfer payment = (CardsTransfer) document;
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		GateMessage message = serviceFacade.createRequest(Constants.CARD_TO_CARD_INFO_REQUEST);
		fillRequest(message, payment);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		payment.setCommission(getCommission(response, getAmount(payment).getCurrency()));
	}

	private void fillRequest(GateMessage message, CardsTransfer transfer)
	{
		Money amount = getAmount(transfer);
		//DebitCard Информаци по карте списания
		RequestHelper.appendCardInfForCardsTransfer(message, transfer.getChargeOffCard());
		//CurrCode Валюта операции
		message.addParameter("CurrCode", amount.getCurrency().getCode());
		//CreditCard Номер карты зачисления
		message.addParameter("CreditCard", transfer.getReceiverCard());
		//Summa Сумма платежа
		RequestHelper.appendSumma(message, amount, "Summa");
	}

	private Money getAmount(CardsTransfer transfer)
	{
		return transfer.getChargeOffAmount() == null ? transfer.getDestinationAmount() : transfer.getChargeOffAmount();
	}

	private Money getCommission(Document response, Currency currency) throws GateException
	{
		String strMoney = XmlHelper.getSimpleElementValue(response.getDocumentElement(), Constants.COMISSION_FIELD_NAME).trim();
		//Устанавливаем валюту операции
		return new Money(new BigDecimal(strMoney), currency);
	}
}
