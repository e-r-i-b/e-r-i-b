package com.rssl.phizicgate.enisey.messaging.mock;

import com.rssl.phizicgate.enisey.messaging.EniseyMessagingService;
import com.rssl.phizicgate.enisey.messaging.EniseyMessageData;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.*;

import org.w3c.dom.Document;

/**
 * @author mihaylov
 * @ created 12.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockEniseyMessagingService extends EniseyMessagingService
{
	private Map<String, MockQHandler> handlersMap = new HashMap<String, MockQHandler>();

	public MockEniseyMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
		handlersMap.put("executeBillingPayment_q",    new ExecuteBillingPayment());
		handlersMap.put("prepareBillingPayment_q",    new PrepareBillingPayment());
		handlersMap.put("requestBillingAttr_q",       new RequestBillingAttr());
		handlersMap.put("revokeBillingPayment_q",     new RevokeBillingPayment());
	}


	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		MockQHandler handler = handlersMap.get(messageInfo.getName());
		if (handler == null)
			throw new GateException("Запрос не поддерживается");
		try
		{
			Document responseDoc = handler.makeRequest(messageData, messageInfo);
			String responseText = XmlHelper.convertDomToText(responseDoc);
			MessageData response = new EniseyMessageData();
			response.setBody(responseText);
			return response;						
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
