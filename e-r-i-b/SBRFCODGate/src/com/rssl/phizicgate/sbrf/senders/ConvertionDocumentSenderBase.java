package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Egorova
 * @ created 23.04.2008
 * @ $Author$
 * @ $Revision$
 * Базовый класс для конверсионных сендеров ЦОДа(в данном случае только перевод счет-счет, счет -карта)
 */
public abstract class ConvertionDocumentSenderBase extends AbstractGatePaymentSender
{
	public ConvertionDocumentSenderBase(GateFactory factory)
	{
		super(factory);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		TransactionCurrencyInfo info = new TransactionCurrencyInfo(getFactory(), gateDocument);
		Currency chargeOffAccountCurrency = info.getChargeOffAccountCurrency();
		Currency destinationAccountCurrency = info.getDestinationAccountCurrency();
		if (!info.isConversion())
		{
			//валюты равны - не конверсия
			return super.getRequestName(gateDocument);
		}
		CurrencyService currencyService = getFactory().service(CurrencyService.class);
		Currency national = currencyService.getNationalCurrency();
		if (chargeOffAccountCurrency.compare(national))
		{
			return "saleCurrencyDemand_q";
		}
		else if (destinationAccountCurrency.compare(national))
		{
			return "purchaseCurrencyDemand_q";
		}
		else
		{
			return "convertCurrencyDemand_q";
		}
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(new TransactionCurrencyInfo(getFactory(), gateDocument).isConversion()))
		{
			//валюты равны - не конверсия
			super.fillGateMessage(gateMessage, gateDocument);
			return;
		}

		AbstractAccountTransfer payment = (AbstractAccountTransfer) gateDocument;
		gateMessage.addParameter("clientId", getClientId(gateDocument.getExternalOwnerId()));
		gateMessage.addParameter("debitAccount", payment.getChargeOffAccount());
		gateMessage.addParameter("creditAccount", getReceiverAccount(payment));
		Money amount = getAmount(payment);
		gateMessage.addParameter("sum", amount.getDecimal().toString());
		String currencyCode = amount.getCurrency().getCode();
		gateMessage.addParameter("currencyCode", currencyCode.equals("RUB") ? "RUR" : currencyCode);

		String operationCode = getRequestHelper().getOperationCode(payment);
		if (StringHelper.isNotEmpty(operationCode))
			gateMessage.addParameter("operationCode", operationCode);
	}
}
