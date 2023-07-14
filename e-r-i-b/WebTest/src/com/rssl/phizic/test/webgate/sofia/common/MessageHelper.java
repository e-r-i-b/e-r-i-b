package com.rssl.phizic.test.webgate.sofia.common;

import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.test.webgate.ryazan.common.MessageUpdate;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.ws.CODMessageData;
import com.rssl.phizicgate.sbrf.ws.mock.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 29.09.2010
 * Time: 15:52:06
 */
public class MessageHelper
{
	private static String ERROR_MESSAGE = "Несоответствие форматов";
	private final static Map<String, MockHandlerSupport> handlerMap;

	static
	{
		handlerMap = new HashMap<String, MockHandlerSupport>();

		handlerMap.put("accountInfoDemand_q", new AccountInfoDemandQHandler());
		handlerMap.put("accountBalanceDemand_q", new AccountBalanceDemandQHandler());
		handlerMap.put("billingDemand_q", new AccountBillingDemandHandler());
		handlerMap.put("form190ResultDemand_q", new LongOfferDemandQHandler());
		handlerMap.put("cardInfoDemand", new CardAcountQHandler());

		handlerMap.put("agreementRegistration_q", new AgreementRegistrationQHandler());
		handlerMap.put("agreementModification_q", new AgreementModificationQHandler());
		handlerMap.put("agreementCancellation_q", new AgreementCancelationQHandler());
		handlerMap.put("agreementCancellationWithoutCharge_q", new AgreementCancelationMandatoryWithOutQHandler());
		handlerMap.put("agreementCancellationMandatory_q", new AgreementCancelationMandatoryQHandler());
		handlerMap.put("revokeOperation_q", new RevokeOperationQHandler());

		handlerMap.put("convertCurrencyDemand_q", new OfflineHandler());
		handlerMap.put("purchaseCurrencyDemand_q", new OfflineHandler());
		handlerMap.put("saleCurrencyDemand_q", new OfflineHandler());
		handlerMap.put("executePayment_q", new OfflineHandler());
		handlerMap.put("executeBillingPayment", new OfflineHandler());
		handlerMap.put("lossingPassbook_q", new OfflineHandler());
		handlerMap.put("transferAccountToAccountDemand_q", new TransferAccountToAccountDemandQHandler());
		handlerMap.put("transferOtherBank_q", new TransferOtherBankQHandler());
	}

	public String getCurentMessage(String message) throws RemoteException
	{

		if (message.contains("<requestBillingAttr_q>"))
			return new MessageUpdate().requestAttr(message);
		else if (message.contains("<prepareBillingPayment_q>"))
			return new MessageUpdate().preparePayment(message);
		else if (message.contains("<executeBillingPayment_q>"))
			return new MessageUpdate().executePayment(message);
		else if (message.contains("<revokeBillingPayment_q>"))
			return new MessageUpdate().revokePayment(message);
		else
		{
			for (String key : handlerMap.keySet())
			{
				if (message.contains(key))
					try
					{
						MessageData messageData = new CODMessageData();
						messageData.setBody(message);
						MOCKMessageParser parser = new MOCKMessageParser();
						parser.setMessage(messageData);

						MockHandlerSupport handler = handlerMap.get(parser.getMessageType());
						return XmlHelper.convertDomToText(handler.makeRequest(parser.getMessageBody(), null, parser.getMessageId()));
					}
					catch (Exception e)
					{
						throw new RemoteException(e.getMessage());
					}
			}

			throw new RemoteException(ERROR_MESSAGE);
		}
	}
}
