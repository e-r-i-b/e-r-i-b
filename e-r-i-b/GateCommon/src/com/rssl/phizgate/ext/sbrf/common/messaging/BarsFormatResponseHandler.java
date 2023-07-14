package com.rssl.phizgate.ext.sbrf.common.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author osminin
 * @ created 15.03.2011
 * @ $Author$
 * @ $Revision$
 * Парсер ответов, использующий фломат(структуру) сообщений БАРСа
 */
public class BarsFormatResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String XSB_REMOTE_CLIENT_NAME_RETURN_TAG = "XsbRemoteClientNameReturn";
	private static final String SSNAME_TAG = "SSName";
	private static final String SINN_TAG = "SInn";

	private State state;
	private ResponseBodyHandler bodyHandler;

	private enum State
	{
		xsbRemoteClientNameReturnTag,
		sSNameTag,
		sInnTag,
		end,
	}

	public BarsFormatResponseHandler(MessageInfo messageInfo)
	{
		reset();
		bodyHandler = new ResponseBodyHandler(messageInfo.getResponses());
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		switch(state)
		{
			case xsbRemoteClientNameReturnTag:
				bodyHandler.startDocument();
				bodyHandler.startElement(uri, localName, qName, attributes);
				assertTag(qName, XSB_REMOTE_CLIENT_NAME_RETURN_TAG);
				state = State.sSNameTag;
				break;
			case sSNameTag:
				bodyHandler.startElement(uri, localName, qName, attributes);
				assertTag(qName, SSNAME_TAG);
				break;
			case sInnTag:
				bodyHandler.startElement(uri, localName, qName, attributes);
				assertTag(qName, SINN_TAG);
				break;
			default:
				throw new SAXException("Больше ничего не ждем");
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case sSNameTag:
				bodyHandler.endElement(uri, localName, qName);
				state = State.sInnTag;
				break;
			case sInnTag:
				bodyHandler.endElement(uri, localName, qName);
				state = State.xsbRemoteClientNameReturnTag;
				break;
			case xsbRemoteClientNameReturnTag:
				bodyHandler.endElement(uri, localName, qName);
				bodyHandler.endDocument();
				state = State.end;
				break;
			default:
				// do nothing
		}
	}

	public String getMessageId()
	{
		return null;
	}

	public String getResponceTag()
	{
		return XSB_REMOTE_CLIENT_NAME_RETURN_TAG;
	}

	public boolean isSuccess()
	{
		return true;                                                                                               
	}

	public String getErrorCode()
	{
		return null;
	}

	public String getErrorText()
	{
		return null;
	}

	public boolean isVoid()
	{
		return false;
	}

	public void throwException() throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
	}

	public Object getBody()
	{
		return bodyHandler.getResult();
	}

	public void reset()
	{
		state = State.xsbRemoteClientNameReturnTag;
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		bodyHandler.characters(ch, start, length);
	}

	private void assertTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
			throw new SAXException("Ожпдается тег: " + tag + ". Найден тег: " + qName);
	}
}
