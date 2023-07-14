package com.rssl.phizicgate.asbc.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import com.rssl.phizic.utils.RandomGUID;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Krenev
 * @ created 03.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String MESSAGE_TAG = "response";
	private static final String INVALID_ACCOUNT_TAG = "invalidAccount";
	private static final String DATA_NOT_FOUND_TAG = "dataNotFound";
	// прочитанные данные
	private String messageId;
	private String responceTag;
	private int bodyLevel;
	private String tagText;
	private String errorText;

	private enum State
	{
		messageTag,
		startBody,
		continueBody,
		end, invalidAccountTag, dataNotFoundTag,
	}

	private enum ResultType
	{
		body,
		error,
		success,
	}

	// состояние парсера
	private ResultType resultType;
	private State state;
	private ResponseBodyHandler bodyHandler;

	public ASBCResponseHandler(MessageInfo messageInfo)
	{
		reset();
		bodyHandler = new ResponseBodyHandler(messageInfo.getResponses());
	}

	/**
	 * Обнулить состояние парсера. После этого он снова готов к работе
	 */
	public void reset()
	{
		state = State.messageTag;
		resultType = null;
		messageId = null;
		responceTag = null;
		bodyLevel = -1;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public String getResponceTag()
	{
		return responceTag;
	}

	public boolean isSuccess()
	{
		return resultType != ResultType.error;
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
		return resultType == ResultType.success;
	}

	public void throwException()
			throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
		if (resultType != ResultType.error)
		{
			return;
		}
		throw new GateMessagingClientException(errorText);
	}

	public Object getBody()
	{
		if (resultType == ResultType.body)
			return bodyHandler.getResult();

		return null;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		tagText = "";
		switch (state)
		{
			case messageTag:
				assertTag(qName, MESSAGE_TAG);
				messageId = new RandomGUID().getStringValue();
				state = State.startBody;
				break;
			case startBody:
				resultType = ResultType.body;
				state = State.continueBody;
				bodyLevel = 0;
				bodyHandler.startDocument();
				continueBody(uri, localName, qName, attributes);
				break;
			case continueBody:
				continueBody(uri, localName, qName, attributes);
				break;
			default:
				throw new SAXException("Больше ничего не ждем");
		}
	}

	private void continueBody(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		bodyLevel++;
		bodyHandler.startElement(uri, localName, qName, attributes);
		if (INVALID_ACCOUNT_TAG.equals(qName))
		{
			state = State.invalidAccountTag;
		}
		if (DATA_NOT_FOUND_TAG.equals(qName))
		{
			state = State.dataNotFoundTag;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case dataNotFoundTag:
			case invalidAccountTag:
				errorText = tagText;
				state = State.continueBody;
				endBody(uri, localName, qName);
				resultType = ResultType.error;
				break;
			case continueBody:
				endBody(uri, localName, qName);
				break;
			default:
				// do nothing
		}
	}

	private void endBody(String uri, String localName, String qName) throws SAXException
	{
		bodyLevel--;
		bodyHandler.endElement(uri, localName, qName);
		if (bodyLevel == 0)
		{
			if (responceTag == null)
				responceTag = qName;

			bodyHandler.endDocument();
			state = State.end;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		switch (state)
		{
			case continueBody:
			case dataNotFoundTag:
			case invalidAccountTag:
				bodyHandler.characters(ch, start, length);
			default:
				tagText += new String(ch, start, length);
		}
	}

	private void assertTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ожидался тег: " + tag + ".Найден тег:" + qName);
		}
	}
}
