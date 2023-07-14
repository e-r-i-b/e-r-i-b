package com.rssl.phizgate.ext.sbrf.common.messaging;

import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.gate.messaging.*;
import com.rssl.phizic.gate.messaging.impl.EmptyBodyHandler;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: krenev_a $
 * @ $Revision: 69122 $
 * Парсер ответов, использующий формат(структруру) сообщений ЦОДа.
 * например ЦПФЛ имеет тот же формат запросов(заголовки) что и ЦОД.
 */

public class CODFormatResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String ACKNOWLEDGE_T_TAG = "acknowledge_t";
	private static final String MESSAGE_TAG       = "message";
	private static final String PARENT_TAG        = "parentId";

	//todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
	public static final String MESSAGE_ID_TAG    = "messageId";

	private static final String MESSAGE_DATE_TAG  = "messageDate";
	private static final String FROM_ABONENT_TAG  = "fromAbonent";
	private static final String REFUSAL_T_TAG     = "refusal_t";
	private static final String ERROR_A_TAG       = "error_a";

	private enum State
	{
		messageTag,
		messageIdTag,
		messageDateTag,
		fromAbonentTag,
		startBody,
		continueBody,
		end,
		parentMessage,
		parentMessageId,
		parentMessageDate,
		parentFromAbonent,
	}

	private enum ResultType
	{
		/**
		 * TODO другой результат
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
	private String   messageId;
	private String   responceTag;
	private Calendar messageDate;
	private String   fromAbonent;
	//ссылка на родительское сообщение, считываеться если есть
	private String   parentMessageId;
	private Calendar parentMessageDate;
	private String   parentFromAbonent;

	private ContentHandler      bodyHandler;
	private DefaultErrorMessageHandler errorHandler;

	private int             bodyLevel;

	public CODFormatResponseHandler(MessageInfo messageInfo)
	{
		this(messageInfo.getResponses());
	}

	/**
	 * Конструктор
	 * @param allowedRootTags список разрешенных root тегов,
	 * т.е. <message><root></root></message>
	 */
	public CODFormatResponseHandler(Set<String> allowedRootTags)
	{
		reset();
		this.bodyHandler = new ResponseBodyHandler(allowedRootTags);
	}

	/**
	 * Обнулить состояние парсера. После этого он снова готов к работе
	 */
	public void reset()
	{
		state         = State.messageTag;
		resultType    = null;
		tagText       = null;
		errorHandler  = null;
		bodyLevel     = -1;
		messageId     = null;
		messageDate   = null;
		fromAbonent   = null;
		parentMessageId     = null;
		parentMessageDate   = null;
		parentFromAbonent   = null;
		responceTag   = null;
	}

	public Calendar getParentMessageDate()
	{
		return parentMessageDate;
	}

	public String getParentMessageId()
	{
		return parentMessageId;
	}

	public String getFromAbonent()
	{
		return fromAbonent;
	}

	public Calendar getMessageDate()
	{
		return messageDate;
	}

	public String getResponceTag()
	{
		return responceTag;
	}

	public String getParentFromAbonent()
	{
		return parentFromAbonent;
	}
	public String getMessageId()
	{
		return messageId;
	}

	public ContentHandler getBodyHandler()
	{
		return bodyHandler;
	}

	public boolean isSuccess()
	{
		return resultType != ResultType.error;
	}

	public String getErrorCode()
	{
		if(errorHandler != null)
			return errorHandler.getErrorCode();
		return null;
	}

	public String getErrorText()
	{
		if(errorHandler != null)
			return errorHandler.getErrorMessage();
		return null;
	}

	public boolean isVoid()
	{
		return resultType == ResultType.success;
	}

	public void throwException()
			throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
        if ( errorHandler != null )
            errorHandler.throwException();
    }

	public Object getBody()
	{
		if(resultType == ResultType.body)
		{
			Document doc =  ((ResponseBodyHandler) bodyHandler).getResult();
			//todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
			if( (doc != null))
			{
				if((messageId != null))
					XmlHelper.appendSimpleElement(doc.getDocumentElement(),MESSAGE_ID_TAG, messageId);

				if(messageDate != null)
					XmlHelper.appendSimpleElement(doc.getDocumentElement(), MESSAGE_DATE_TAG, XMLDatatypeHelper.formatDate(messageDate));
			}

			return doc;
		}
		else if(resultType == ResultType.success)
		{//todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
			try
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				Document document = factory.newDocumentBuilder().newDocument();
				Element elem = document.createElement("message");

				document.appendChild(elem);
				XmlHelper.appendSimpleElement(elem,MESSAGE_ID_TAG, messageId);
				XmlHelper.appendSimpleElement(elem, MESSAGE_DATE_TAG, XMLDatatypeHelper.formatDate(messageDate));
				return document;
			}
			catch (ParserConfigurationException e)
			{
				throw new RuntimeException(e);
			}
		}

		
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
				initBodyProcessing(qName);
			case continueBody:
				bodyLevel++;
				bodyHandler.startElement(uri, localName, qName, attributes);
				break;
			case parentMessage:
				assertTag(qName, PARENT_TAG);
				state = State.parentMessageId;
				break;
			case parentMessageId:
				assertTag(qName, MESSAGE_ID_TAG);
				break;
			case parentMessageDate:
				assertTag(qName, MESSAGE_DATE_TAG);
				break;
			case parentFromAbonent:
				assertTag(qName, FROM_ABONENT_TAG);
				break;
			default:
				throw new SAXException("Больше ничего не ждем");
		}
	}

	private void initBodyProcessing(String qName) throws SAXException
	{
		if(qName.equals(ACKNOWLEDGE_T_TAG))
		{
			responceTag = qName;
			resultType = ResultType.success;
			bodyHandler = new EmptyBodyHandler();
		}
		else if(qName.equals(REFUSAL_T_TAG) || qName.equals(ERROR_A_TAG))
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
					if(responceTag==null)
						responceTag = qName;
					
					bodyHandler.endDocument();
					state = State.parentMessage;
				}
				break;
			case parentMessageId:
				parentMessageId = tagText;
				state = State.parentMessageDate;
				break;
			case parentMessageDate:
				if (!StringHelper.isEmpty(tagText))
					parentMessageDate = XMLDatatypeHelper.parseDate(tagText);
				else parentMessageDate = null;
				state = State.parentFromAbonent;
				break;
			case parentFromAbonent:
				parentFromAbonent = tagText;
				state = State.end;
				break;
			default:
				// do nothing
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		switch(state)
		{
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
			throw new SAXException("Ожидался тег: " + tag + ".Найден тег:" +qName);
		}
	}

	private ContentHandler createErrorHandler()
	{
		errorHandler = new DefaultErrorMessageHandler();
		return errorHandler;
	}
}