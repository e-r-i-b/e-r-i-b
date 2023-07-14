package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.phizic.gate.messaging.*;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gladishev
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveMessageHandler extends DefaultHandler implements ResponseHandler, MessageInfoContainer
{
	public static final String MESSAGE_ID_TAG = "messageId";
	public static final String MESSAGE_DATE_TAG = "messageDate";
	public static final String PARENT_MESSAGE_ID_TAG = "parentMessageId";
	public static final String PARENT_MESSAGE_DATE_TAG = "parentMessageDate";

	// прочитанные данные
	private String messageTag;
	private String tagText;

	// состояние парсера
	private ResultType resultType;
	private State state;
	private IQWaveResponseHeaderHandler headHandler;
	private ResponseBodyHandler bodyHandler;
	private int bodyLevel;
	private int headLevel;
	private Set<String> allowedStartTags;

	private enum State
	{
		start,
		startHead,
		continueHead,
		startBody,
		continueBody,
		end
	}

	private enum ResultType
	{
		head,
		body,
		error,
		success
	}

	public IQWaveMessageHandler(Set<String> allowedTags)
	{

		this.allowedStartTags = allowedTags;
		reset();
	}

	public IQWaveMessageHandler(MessageInfo messageInfo)
	{
		this(messageInfo.getResponses());
	}

	/**
	 * Обнулить состояние парсера. После этого он снова готов к работе
	 */
	public void reset()
	{
		state = State.start;
		resultType = null;
		headLevel = -1;
		bodyLevel = -1;
		headHandler = new IQWaveResponseHeaderHandler();
		Set<String> bodyEl = new HashSet<String>();
		bodyEl.add(Constants.BODY_TAG);
		bodyHandler = new ResponseBodyHandler(bodyEl);
	}

	public String getMessageTag()
	{
		return messageTag;
	}

	public String getMessageId()
	{
		return headHandler.getMessageId();
	}

	public Calendar getMessageDate()
	{
		return headHandler.getMessageDate();
	}

	public String getFromAbonent()
	{
		return headHandler.getFromAbonent();
	}

	public String getParentMessageId()
	{
		return headHandler.getParentMessageId();
	}

	public Calendar getParentMessageDate()
	{
		return headHandler.getParentMessageDate();
	}

	public String getParentFromAbonent()
	{
		return headHandler.getParentFromAbonent();
	}

	public String getResponceTag()
	{
		return getMessageTag();
	}

	public boolean isSuccess()
	{
		return resultType != ResultType.error;
	}

	public boolean isVoid()
	{
		return resultType == ResultType.success;
	}

	public void throwException() throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException, GateTimeOutException
	{
		headHandler.throwException();
	}

	public Object getBody()
	{
		Document doc = getBodyContent();
		if (doc == null)
		{
			return null;
		}
		//todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
		if(doc != null)
		{
			String parentMessageId = getParentMessageId();
			if (parentMessageId != null)
				XmlHelper.appendSimpleElement(doc.getDocumentElement(), PARENT_MESSAGE_ID_TAG, parentMessageId);

			Calendar parentMessageDate = getParentMessageDate();
			if (parentMessageDate != null)
				XmlHelper.appendSimpleElement(doc.getDocumentElement(), PARENT_MESSAGE_DATE_TAG, XMLDatatypeHelper.formatDate(parentMessageDate));
		}


		return doc;
	}

	public Document getBodyContent()
	{
		if (resultType != ResultType.error)
		{
			return bodyHandler.getResult();
		}
		return null;
	}

	public String getErrorCode()
	{
		if (isSuccess())
		{
			return null;
		}
		return headHandler.getErrorCode();
	}

	public String getErrorText()
	{
		return getErrorDescription();
	}

	public String getErrorDescription()
	{
		if (isSuccess())
		{
			return null;
		}
		return headHandler.getErrorText();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		tagText = "";
		switch (state)
		{
			case start:
				assertStartTag(qName);
				state = State.startHead;
				break;
			case startHead:
				assertTag(qName, Constants.HEAD_TAG);
				resultType = ResultType.head;
				state = State.continueHead;
				headLevel = 0;
				headHandler.startDocument();
				continueHead(uri, localName, qName, attributes);
				break;
			case continueHead:
				continueHead(uri, localName, qName, attributes);
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
			case end:
				//nothing
				break;
			default:
				throw new SAXException("Больше ничего не ждем");
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case continueHead:
				headLevel--;
				headHandler.endElement(uri, localName, qName);
				if (headLevel == 0)
				{
					headHandler.endDocument();

					if (headHandler.isError())
					{
						resultType = ResultType.error;
						state = State.end;
					}
					else
					{
						String messageType = headHandler.getMessageType();
						if (!messageType.equals(messageTag))
							throw new NotExpectedResponseException("Неверный ответ : MessType=" + qName);

						state = State.startBody;
					}
				}
				break;
			case continueBody:
				bodyLevel--;
				bodyHandler.endElement(uri, localName, qName);
				if (bodyLevel == 0)
				{
					if (messageTag == null)
						messageTag = qName;

					bodyHandler.endDocument();
					state = State.end;
				}
				break;
			case startBody:
				//попали сюда, когда тег боди отсутсвует. вставляем боди сами.
				Attributes emptyAttributes = new AttributesImpl();
				bodyHandler.startDocument();
				bodyHandler.startElement("", "", Constants.BODY_TAG, emptyAttributes);
				bodyHandler.endElement("", "", Constants.BODY_TAG);
				bodyHandler.endDocument();
				state = State.end;
				break;
			default:
				// do nothing
		}
	}

	private void continueBody(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		bodyLevel++;
		bodyHandler.startElement(uri, localName, qName, attributes);
	}

	private void continueHead(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		headLevel++;
		headHandler.startElement(uri, localName, qName, attributes);
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		switch (state)
		{
			case continueHead:
				headHandler.characters(ch, start, length);
				break;
			case continueBody:
				bodyHandler.characters(ch, start, length);
				break;
			default:
				tagText += new String(ch, start, length);
		}
	}

	private void assertStartTag(String qName) throws SAXException
	{
		if (!allowedStartTags.contains(qName))
			throw new NotExpectedResponseException("Неверный ответ : " + qName);

		messageTag = qName;
	}

	private void assertTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ожидался тег: " + tag + ".Найден тег:" + qName);
		}
	}
}
