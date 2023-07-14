package com.rssl.phizic.gorod.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gorod.messaging.XMLMessagingService;
import com.rssl.phizic.gorod.messaging.GorodMessageData;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Gainanov
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockXMLMessagingService extends XMLMessagingService
{
	private Map<String, MockHandlerSupport> handlerMap;

	public MockXMLMessagingService(GateFactory factory) throws GateException
	{
		super(factory);

		handlerMap = new HashMap<String, MockHandlerSupport>();

		handlerMap.put("ReqCard",       new ReqCardQHandler());
		handlerMap.put("ReqCalcEOrder", new ReqCalcEOrderQHandler());
		handlerMap.put("ReqDelEOrder",  new ReqDelEOrderQHandler());
		handlerMap.put("ReqSaveEOrder", new ReqSaveEOrderQHandler());
		handlerMap.put("ReqPayAdvice",  new ReqPayAdviceQHandler());
		handlerMap.put("ReqXObject",    new ReqXObjectQHandler());
		handlerMap.put("ReqCursor",     new ReqCursorQHandler());
		handlerMap.put("ReqGlobal",     new ReqGlobalQHandler());
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		try{
		;
		MockHandlerSupport handler = handlerMap.get(messageInfo.getName());

		Document responseDoc;
		if (handler == null)
			throw new GateException("Запрос не поддерживается");
		else
			responseDoc = handler.makeRequest(XmlHelper.parse(messageData.getBodyAsString("windows-1251")), messageInfo);

		GorodMessageData data = new GorodMessageData();
		data.setBody(XmlHelper.convertDomToText(responseDoc).getBytes("windows-1251"));
		data.setBody(data.getBodyAsString("windows-1251").replace("UTF-8", "windows-1251").getBytes());
		return data; 
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}
}
