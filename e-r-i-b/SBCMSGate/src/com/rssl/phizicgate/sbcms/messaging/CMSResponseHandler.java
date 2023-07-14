package com.rssl.phizicgate.sbcms.messaging;

import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
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
 * todo. Комментарии оставлены на случай, если из ПЦ сообщение все таки с <message>...</message> придет. Убрать как станет понятно.
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CMSResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final CounterService service = new CounterService();
	
	private static final String MESSAGE_TAG       = "POSGATE_MSG";
//	private static final String MESSAGE_ID_TAG    = "messageId";
//	private static final String MESSAGE_DATE_TAG  = "messageDate";
//	private static final String FROM_ABONENT_TAG  = "fromAbonent";

	// прочитанные данные
	private String   messageId;
	private String   responceTag;
//	private Calendar messageDate;
//	private String   fromAbonent;

	private enum State
	{
		messageTag,
//		messageIdTag,
//		messageDateTag,
//		fromAbonentTag,
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
		 * ошибка (WAY4_ERROR)
		 */
		error,
		/**
		 * успех (При подтвержении offline сообщений, здесь не используется)
		 */
		success,
	}

	// состояние парсера
	private ResultType resultType;
	private State      state;
	private String     tagText;

	private ContentHandler bodyHandler;
	private DefaultErrorMessageHandler errorHandler;

	private int bodyLevel;

	public CMSResponseHandler(MessageInfo messageInfo)
	{
		reset();
		this.bodyHandler = new ResponseBodyHandler(messageInfo.getResponses());
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
//		messageDate   = null;
//		fromAbonent   = null;
		responceTag   = null;
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
		if (errorHandler != null)
			return errorHandler.getErrorCode();
		return null;
	}

	public String getErrorText()
	{
		if (errorHandler != null)
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
		try
		{
			switch(state)
			{
				case messageTag:
					assertTag(qName, MESSAGE_TAG);
					messageId = service.getNext(Counters.CMS_MSG_NUMBER).toString();
					state = State.startBody;
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
		catch(CounterException e)
		{
			throw new SAXException(e);
		}
	}

	private void initBodyProcessing(String qName) throws SAXException
	{
		resultType = ResultType.body;
		state = State.continueBody;
		bodyLevel = 0;
		bodyHandler.startDocument();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case continueBody:
				bodyLevel--;
				bodyHandler.endElement(uri, localName, qName);
				if(bodyLevel == 0)
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
		switch(state)
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
			throw new SAXException("Ожидался тег: " + tag + ".Найден тег:" +qName);
		}
	}

}
