package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizicgate.sbrf.ws.CODWebBankServiceSupport;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */
public class MockWebBankServiceFacade extends CODWebBankServiceSupport
{
	private Map<String, MockHandlerSupport> handlerMap;

	public MockWebBankServiceFacade(GateFactory factory) throws GateException
	{
		super(factory);

		handlerMap = new HashMap<String, MockHandlerSupport>();

		handlerMap.put("accountInfoDemand_q",              new AccountInfoDemandQHandler());
		handlerMap.put("accountBalanceDemand_q",           new AccountBalanceDemandQHandler());
		handlerMap.put("billingDemand_q",                  new AccountBillingDemandHandler());
		handlerMap.put("form190ResultDemand_q",            new LongOfferDemandQHandler());
		handlerMap.put("cardInfoDemand",                   new CardAcountQHandler());

		handlerMap.put("agreementRegistration_q",          new AgreementRegistrationQHandler());
		handlerMap.put("agreementModification_q",          new AgreementModificationQHandler());
		handlerMap.put("agreementCancellation_q",          new AgreementCancelationQHandler());
		handlerMap.put("agreementCancellationWithoutCharge_q", new AgreementCancelationMandatoryWithOutQHandler());
		handlerMap.put("agreementCancellationMandatory_q",     new AgreementCancelationMandatoryQHandler());
		handlerMap.put("revokeOperation_q",                    new RevokeOperationQHandler());

		handlerMap.put("convertCurrencyDemand_q",          new OfflineHandler());
		handlerMap.put("purchaseCurrencyDemand_q",         new OfflineHandler());
		handlerMap.put("saleCurrencyDemand_q",             new OfflineHandler());
		handlerMap.put("executePayment_q",                 new OfflineHandler());
		handlerMap.put("executeBillingPayment",          new OfflineHandler());
		handlerMap.put("lossingPassbook_q",                new OfflineHandler());
		handlerMap.put("transferAccountToAccountDemand_q", new TransferAccountToAccountDemandQHandler());
		handlerMap.put("transferOtherBank_q",              new TransferOtherBankQHandler());
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		MOCKMessageParser parser = new MOCKMessageParser();
		parser.setMessage(messageData);

		MockHandlerSupport handler = handlerMap.get(parser.getMessageType());
		String requestId = parser.getMessageId();
		Document responseDoc;
		if (handler == null)
			responseDoc = makeValidationError("MockWebBankServiceFacade не поддерживает запрос с типом " + parser.getMessageType(), requestId);
		else
		{
			try
			{
				handler.validate(parser.getMessageDocument(),messageInfo);
				responseDoc = handler.makeRequest(parser.getMessageBody(), messageInfo, requestId);
			}
			catch(GateMessagingValidationException e)
			{
				responseDoc = makeValidationError(e.getMessage(), requestId);
			}
		}

		return parser.makeMessage(responseDoc);
	}

	private Document makeValidationError(String errorMessage, String requestId) throws GateException
	{
		ErrorAHandler handler = new ErrorAHandler(DefaultErrorMessageHandler.ERROR_VALIDATE_CODE, errorMessage);

		// ErrorAHandler не использует параметры
		return handler.makeRequest(null, null, requestId);
	}
}
