package com.rssl.phizicgate.rsV51.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Evgrafov
 * @ created 09.03.2007
 * @ $Author: mihaylov $
 * @ $Revision: 44946 $
 */

public class RetailResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String MESSAGE_TAG      = "response";
	private static final String MESSAGE_ID_TAG   = "id";
	private static final String ERROR_A_TAG      = "error_a";

	private enum State
	{
		messageTag,
		messageIdTag,
		startBody,
		continueBody,
		end,
	}

	private enum ResultType
	{
		/**
		 * сообщение с содержанием
		 */
		body,
		/**
		 * ошибка (refusal_t, error_a)
		 */
		error,
		/**
		 * успех (acknowledge_t)
		 */
		success,
	}

	// состояние парсера
	private ResultType resultType;
	private State      state;
	private String     tagText;

	// прочитанные данные
	private String     messageId;
	private String     responceTag;

	private ContentHandler bodyHandler;
	private DefaultErrorMessageHandler errorHandler;

	private int bodyLevel;

	public RetailResponseHandler(MessageInfo messageInfo)
	{
		reset();
		this.bodyHandler = new ResponseBodyHandler(messageInfo.getResponses());
	}

	/**
	 * Обнулить состояние парсера. После этого он снова готов к работе
	 */
	public void reset()
	{
		state        = State.messageTag;
		resultType   = null;
		tagText      = null;
		errorHandler = null;
		bodyLevel    = -1;
		messageId    = null;
		responceTag  = null;
	}

	public String getResponceTag()
	{
		return responceTag;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public boolean isSuccess()
	{
		return resultType != ResultType.error;
	}

	public String getErrorCode()
	{
		if (errorHandler != null)
			return errorHandler.getErrorCode();
		return null;
	}

	public boolean isVoid()
	{
		return resultType == ResultType.success;
	}

	public void throwException()
			throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
		if (errorHandler != null)
			errorHandler.throwException();
	}

	public Object getBody()
	{
		if (resultType == ResultType.body)
			return ((ResponseBodyHandler) bodyHandler).getResult();

		return null;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		switch (state)
		{
			case messageTag:
				assertTag(qName, MESSAGE_TAG);
				state = State.messageIdTag;
				break;
			case messageIdTag:
				assertTag(qName, MESSAGE_ID_TAG);
				break;
			case startBody:
				initBodyProcessing(qName);
			case continueBody:
				bodyLevel++;
				bodyHandler.startElement(uri, localName, qName, attributes);
				break;
			default:
				throw new SAXException("Больше ничего не ждем");
		}
	}

	private void initBodyProcessing(String qName) throws SAXException
	{
		if (qName.equals(ERROR_A_TAG))
		{
			responceTag = qName;
			resultType = ResultType.error;
			bodyHandler = createErrorHandler();
		}
		else
		{
			resultType = ResultType.body;
		}
		state = State.continueBody;
		bodyLevel = 0;
		bodyHandler.startDocument();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case messageIdTag:
				messageId = tagText;
				state = State.startBody;
				break;
			case continueBody:
				bodyLevel--;
				bodyHandler.endElement(uri, localName, qName);
				if (bodyLevel == 0)
				{
					if(responceTag==null)
						responceTag = qName;
					
					bodyHandler.endDocument();
					state = State.end;
				}
				break;
			default:
				// do nothing
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		switch (state)
		{
			case continueBody:
				bodyHandler.characters(ch, start, length);
				break;
			default:
				tagText = new String(ch, start, length);
		}
	}

	private void assertTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ожидался тег " + tag);
		}
	}

	private ContentHandler createErrorHandler()
	{
		errorHandler = new DefaultErrorMessageHandler();
		return errorHandler;
	}
}