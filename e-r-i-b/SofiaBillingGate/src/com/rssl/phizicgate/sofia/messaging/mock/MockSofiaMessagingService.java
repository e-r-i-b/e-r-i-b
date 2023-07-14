package com.rssl.phizicgate.sofia.messaging.mock;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sofia.messaging.Constants;
import com.rssl.phizicgate.sofia.messaging.SofiaMessageData;
import com.rssl.phizicgate.sofia.messaging.SofiaMessagingService;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockSofiaMessagingService extends SofiaMessagingService
{
	private Map<String, MockQHandler> handlerMap = new HashMap<String, MockQHandler>();

	public MockSofiaMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
		handlerMap.put(Constants.ATTRIBUTES_REQUEST, new RequestBillAttrQHandler());
		handlerMap.put(Constants.PREPARE_REQUEST, new PrepareBillPaymentQHandler());
		handlerMap.put(Constants.EXECUTE_REQUEST, new ExecuteBillPaymentQHandler());
		handlerMap.put(Constants.REVOKE_REQUEST, new RevokeBillPaymentQHandler());
	}

	public MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		MockQHandler handler = handlerMap.get(messageInfo.getName());

		if (handler == null)
			throw new GateException("Запрос не поддерживается");
		try
		{
			Document responseDoc = handler.makeRequest(messageData, messageInfo);
			return new SofiaMessageData(XmlHelper.convertDomToText(responseDoc));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
