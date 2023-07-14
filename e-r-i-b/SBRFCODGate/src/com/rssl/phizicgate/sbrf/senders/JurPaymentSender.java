package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer;
import com.rssl.phizic.gate.payments.AccountJurTransfer;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Egorova
 * @ created 25.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class JurPaymentSender extends AbstractDocumentSender
{
	public JurPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return "executePayment_q";
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		Class<? extends GateDocument> type = gateDocument.getType();
		if (!(AccountJurTransfer.class == type || AccountJurIntraBankTransfer.class == type))
		{
			throw new GateException("Неверный тип платежа: " + type + ", должен быть: AccountJurTransfer или AccountJurIntraBankTransfer.");
		}

		AccountJurTransfer rusPayment = (AccountJurTransfer) gateDocument;

		gateMessage.addParameter("clientId", getClientId(gateDocument.getExternalOwnerId()));

		gateMessage.addParameter("account", rusPayment.getReceiverAccount());
		ResidentBank residentBank = rusPayment.getReceiverBank();
		gateMessage.addParameter("bankBIC", residentBank.getBIC());
		if(!StringHelper.isEmpty(residentBank.getAccount()))
	        gateMessage.addParameter("bankAccount", residentBank.getAccount());
		if(!StringHelper.isEmpty(rusPayment.getReceiverINN()))
	        gateMessage.addParameter("inn", rusPayment.getReceiverINN());

		gateMessage.addParameter("sum", rusPayment.getDestinationAmount().getDecimal().toString());
		if (rusPayment.getCommission() != null)
		{
			gateMessage.addParameter("commision", rusPayment.getCommission().getDecimal().toString());
		}
 		gateMessage.addParameter("debitAccount", rusPayment.getChargeOffAccount());
		gateMessage.addParameter("purpose", processGround(rusPayment));
		gateMessage.addParameter( "beneficiary", StringHelper.replaceQuotes(rusPayment.getReceiverName() ));
		//поля нет в описании ЦОД, но есть в xsd. Разработчик ЦОД сказал должно быть.
		String usePaymentOrder = getUsePaymentOrder();
		if (usePaymentOrder != null)
		{
			gateMessage.addParameter("usePaymentOrder", usePaymentOrder);
		}
	}

	protected String getUsePaymentOrder()
	{
		return ConfigFactory.getConfig(SpecificGateConfig.class).getUsePaymentOrderForAccountJurTransfer();
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		Class<? extends GateDocument> type = document.getType();
		if (!(AccountJurTransfer.class == type || AccountJurIntraBankTransfer.class == type))
		{
			throw new GateException("Неверный тип платежа: " + type + ", должен быть: AccountJurTransfer или AccountJurIntraBankTransfer.");
		}

		AccountJurTransfer accountJurTransfer = (AccountJurTransfer) document;
		Currency chargeOffcurrency= getAccountCurrency(accountJurTransfer.getChargeOffAccount());
		Currency receiverAccountCurrency= getAccountCurrency(accountJurTransfer.getReceiverAccount());
		if (!chargeOffcurrency.compare(receiverAccountCurrency))
		{
			throw new GateLogicException("Вы не можете совершить перевод в разных валютах. Выберите счета в одной валюте");
		}
		if(accountJurTransfer.getReceiverAccount().length() != 20)
			throw new GateLogicException("Номер счета должен содержать 20 цифр");

	}
}
