package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.ErrorProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveResponseHeaderHandler extends DefaultHandler
{
	private State state;
	private String messageId;
	private Calendar messageDate;
	private String fromAbonent;
	private String parentMessageId;
	private Calendar parentMessageDate;
	private String parentFromAbonent;
	private String tagText;
	private String errorCode;
	private String errorText;
	private String messageType;

	public IQWaveResponseHeaderHandler()
	{
		state = State.messageHeadTag;
	}

	private enum State
	{
		messageHeadTag,
		messageUIDTag,
		messageIdTag,
		messageDateTag,
		fromAbonentTag,
		messageTypeTag,
		versionTag,
		errorTag,
		errorCodeTag,
		errorMessageTag,
		parentIdTag,
		parentMessageIdTag,
		parentMessageDateTag,
		parentFromAbonentTag,
		end
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

	public String getErrorCode()
	{
		return errorCode;
	}

	public String getErrorText()
	{
		return errorText;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public String getParentMessageId()
	{
		return parentMessageId;
	}

	public Calendar getParentMessageDate()
	{
		return parentMessageDate;
	}

	public String getParentFromAbonent()
	{
		return parentFromAbonent;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		tagText = "";
		switch (state)
		{
			case messageHeadTag:
				assertTag(qName, Constants.HEAD_TAG);
				state = State.messageUIDTag;
				break;
			case messageUIDTag:
				assertTag(qName, Constants.MESSAGE_UID_TAG);
				state = State.messageIdTag;
				break;
			case messageIdTag:
				assertTag(qName, Constants.MESSAGE_ID_TAG);
				break;
			case messageDateTag:
				assertTag(qName, Constants.MESSAGE_DATE_TAG);
				break;
			case fromAbonentTag:
				assertTag(qName, Constants.FROM_ABONENT_TAG);
				break;
			case messageTypeTag:
				assertTag(qName, Constants.MESSAGE_TYPE_TAG);
				break;
			case versionTag:
				assertTag(qName, Constants.VERSION_TAG);
				break;
			case errorTag:
				assertTag(qName, Constants.ERROR_TAG);
				state = State.errorCodeTag;
				break;
			case errorCodeTag:
				assertTag(qName, Constants.ERROR_CODE_TAG);
				break;
			case errorMessageTag:
				assertTag(qName, Constants.ERROR_MESSAGE_TAG);
				break;
			case parentIdTag:
				assertTag(qName, Constants.PARENT_ID_TAG);
				state = State.parentMessageIdTag;
				break;
			case parentMessageIdTag:
				assertTag(qName, Constants.MESSAGE_ID_TAG);
				break;
			case parentMessageDateTag:
				assertTag(qName, Constants.MESSAGE_DATE_TAG);
				break;
			case parentFromAbonentTag:
				assertTag(qName, Constants.FROM_ABONENT_TAG);
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
				state = State.messageUIDTag;
				break;
			case messageUIDTag:
				state = State.messageTypeTag;
				break;
			case messageTypeTag:
				messageType = tagText;
				state = State.versionTag;
				break;
			case versionTag:
				state = State.errorTag;
				break;
			case errorCodeTag:
				if (!"0".equals(tagText) && !"900".equals(tagText))
				{
					errorCode = tagText;
				}
				state = State.errorMessageTag;
				break;
			case errorTag:
				state = State.parentIdTag;
				break;
			case errorMessageTag:
				if (Constants.ERROR_TAG.equals(qName))
				{
					state = State.parentIdTag;
					break;
				}
				errorText = tagText;
				state = State.errorTag;
				break;
			case parentMessageIdTag:
				parentMessageId = tagText;
				state = State.parentMessageDateTag;
				break;
			case parentMessageDateTag:
				parentMessageDate = XMLDatatypeHelper.parseDate(tagText);
				state = State.parentFromAbonentTag;
				break;
			case parentFromAbonentTag:
				parentFromAbonent = tagText;
				state = State.parentIdTag;
				break;
			case parentIdTag:
				state = State.messageHeadTag;
				break;
			case messageHeadTag:
				state = State.end;
				break;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		tagText+= new String(ch, start, length);
	}

	private void assertTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ожидался тег: " + tag + ".Найден тег:" + qName);
		}
	}

	public void throwException() throws GateMessagingClientException, GateMessagingValidationException, GateMessagingException, GateTimeOutException
	{
		if (isError())
		{
			ErrorProcessor.processError(errorCode, errorText);
		}
	}

	public boolean isError()
	{
		return errorCode != null;
	}
}
