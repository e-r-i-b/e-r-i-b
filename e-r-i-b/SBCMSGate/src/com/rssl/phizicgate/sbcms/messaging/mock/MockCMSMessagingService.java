package com.rssl.phizicgate.sbcms.messaging.mock;

import com.rssl.phizicgate.sbcms.messaging.CMSMessagingService;
import com.rssl.phizicgate.sbcms.messaging.CMSMessageData;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.Document;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockCMSMessagingService extends CMSMessagingService
{
	private Map<String, MockHandlerSupport> handlerMap;

	public MockCMSMessagingService() throws GateException
	{
		super();
		handlerMap = new HashMap<String, MockHandlerSupport>();
		handlerMap.put("BALANCE",   new BalanceQHandler());
		handlerMap.put("HISTORY",   new HistoryQHandler());
		handlerMap.put("LOCK_LOST", new LockLostQHandler());
		handlerMap.put("LOCK_STEAL", new LockLostQHandler());
		handlerMap.put("LOCK_ATM",  new LockLostQHandler());
		handlerMap.put("LOCK_OTHER", new LockLostQHandler());
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		MOCKMessageParser parser = new MOCKMessageParser();
		parser.setMessage(messageData);

		MockHandlerSupport handler = handlerMap.get(parser.getMessageType());

		Document responseDoc;
		if (handler == null)
			responseDoc = makeValidationError("MockCMSMessagingService не поддерживает запрос с типом " + parser.getMessageType());
		else
		{
			try
			{
				handler.validate(parser.getMessageDocument(),messageInfo);
				responseDoc = handler.makeRequest(parser.getMessageBody(), messageInfo);
			}
			catch(GateMessagingValidationException e)
			{
				responseDoc = makeValidationError(e.getMessage());
			}
		}

		return parser.makeMessage(responseDoc);
	}

	private Document makeValidationError(String errorMessage) throws GateException
	{
		ErrorAHandler handler = new ErrorAHandler(DefaultErrorMessageHandler.ERROR_VALIDATE_CODE, errorMessage);

		// ErrorAHandler не использует параметры
		return handler.makeRequest(null, null);
	}
}
