package com.rssl.phizicgate.sofia.messaging;

import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class SofiaResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String MESSAGE_TAG       = "message";
	private static final String MESSAGE_ID_TAG    = "messageId";
	private static final String MESSAGE_DATE_TAG  = "messageDate";
	private static final String FROM_ABONENT_TAG  = "fromAbonent";
	private static final String ERROR_CODE_TAG    = "ErrorCode";
	private static final String ERROR_DESCRIPTION_TAG = "ErrorDescription";
	// прочитанные данные
	private Calendar messageDate;
	private String messageId;
	private String fromAbonent;
	private String responseTag;
	private String tagText;
	private String errorCode;
	private String errorText;
	private int bodyLevel;

	private enum State
	{
		messageTag,
		messageIdTag,
		messageDateTag,
		fromAbonentTag,
		responseTag,
		startBody,
		continueBody,
		end,
		errorCodeTag,
		errorDescriptionTag
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

	public SofiaResponseHandler(MessageInfo messageInfo)
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
		bodyLevel = -1;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public Calendar getMessageDate()
	{
		return messageDate;
	}

	public String getFromAbonent()
	{
		return fromAbonent;
	}

	public String getResponceTag()
	{
		return responseTag;
	}

	public String getErrorText()
	{
		return errorText;
	}

	public boolean isSuccess()
	{
		return resultType != ResultType.error;
	}

	public String getErrorCode()
	{
		return errorCode;
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
		switch(state)
		{
			case messageTag:
				assertTag(qName, MESSAGE_TAG);
				state = State.messageIdTag;
				break;
			case messageIdTag:
				assertTag(qName, MESSAGE_ID_TAG);
				break;
			case messageDateTag:
				assertTag(qName, MESSAGE_DATE_TAG);
				break;
			case fromAbonentTag:
				assertTag(qName, FROM_ABONENT_TAG);
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
			case errorCodeTag:
				continueBody(uri, localName, qName, attributes);
				break;
			case errorDescriptionTag:
				assertTag(qName, ERROR_DESCRIPTION_TAG);
				bodyHandler.startElement(uri, localName, qName, attributes);
				break;
			default:
				throw new SAXException("Больше ничего не ждем");
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case messageIdTag:
				messageId = tagText;
				state = State.messageDateTag;
				break;
			case messageDateTag:
				if (!StringHelper.isEmpty(tagText))
					messageDate = XMLDatatypeHelper.parseDate(tagText);
				else messageDate = null;
				state = State.fromAbonentTag;
				break;
			case fromAbonentTag:
				fromAbonent = tagText;
				state = State.startBody;
				break;
			case continueBody:
				bodyLevel--;
				bodyHandler.endElement(uri, localName, qName);
				if(bodyLevel == 0)
				{
					if(responseTag==null)
						responseTag = qName;

					bodyHandler.endDocument();
					state = State.end;
				}
				break;
			case errorCodeTag:
				bodyLevel--;
			    bodyHandler.endElement(uri, localName, qName);
			    if(resultType == ResultType.error)
			        state = State.errorDescriptionTag;
			    else
			        state = State.continueBody;
			    break;
		    case errorDescriptionTag:
			    state = State.continueBody;
			    break;
			default:
				// do nothing
		}
	}

	private void continueBody(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		bodyLevel++;
		bodyHandler.startElement(uri, localName, qName, attributes);
		if (ERROR_CODE_TAG.equals(qName))
		{
			state = State.errorCodeTag;
			responseTag = qName;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		switch(state)
		{
			case errorCodeTag:
				errorCode = new String(ch,start, length);
				bodyHandler.characters(ch, start, length);
				if(errorCode.equals("0"))
					resultType = ResultType.body;
				else
					resultType = ResultType.error;
				break;
			case errorDescriptionTag:
				bodyHandler.characters(ch, start, length);
                errorText = new String(ch, start, length);
				break;
			case continueBody:
				bodyHandler.characters(ch, start, length);
				break;
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
