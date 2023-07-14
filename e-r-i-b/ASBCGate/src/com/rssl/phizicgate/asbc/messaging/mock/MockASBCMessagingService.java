package com.rssl.phizicgate.asbc.messaging.mock;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.asbc.messaging.ASBCMessageData;
import com.rssl.phizicgate.asbc.messaging.ASBCMessagingService;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizgate.common.messaging.mock.FilenameRequestHandler;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 01.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockASBCMessagingService extends ASBCMessagingService
{
	private Map<String, MockRequestHandler> handlersMap = new HashMap<String, MockRequestHandler>();

	public MockASBCMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
//		handlersMap.put("findCredits", new FilenameRequestHandler("com/rssl/phizicgate/asbc/messaging/mock/xml/findCredits_a.xml"));
//		handlersMap.put("findCredits", new FilenameRequestHandler("com/rssl/phizicgate/asbc/messaging/mock/xml/findCredits2_a.xml"));
		handlersMap.put("findCredits", new FilenameRequestHandler("com/rssl/phizicgate/asbc/messaging/mock/xml/findCredits_a_BUG021523.xml"));
		handlersMap.put("makePayment", new FilenameRequestHandler("com/rssl/phizicgate/asbc/messaging/mock/xml/makePayment_a.xml"));
		handlersMap.put("reversePayment", new ReversePaymentQHandler());
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		MockRequestHandler handler = handlersMap.get(messageInfo.getName());
		if (handler == null)
			throw new GateException("Запрос не поддерживается");
		try
		{
			String codePage = GateProperties.getProperties().getProperty("com.rssl.messages.code.page");
			Document request = XmlHelper.parse(messageData.getBodyAsString(codePage));
			Document responseDoc = handler.proccessRequest(request);
			return  new ASBCMessageData(XmlHelper.convertDomToText(responseDoc).getBytes());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
