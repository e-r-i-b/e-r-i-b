package com.rssl.phizic.gorod.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import com.rssl.phizic.utils.RandomGUID;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Gainanov
 * @ created 16.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String CARD_NOTFOUND_CODE = "4100";

	public String getMessageId()
	{
		return messageId;
	}

	private enum State
	{
		messageTag,
		startBody,
		continueBody,
		codeTag,
		errorCodeTag,
		errorDdescriptionTag,
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

	// состо€ние парсера
	private ResultType resultType;
	private State      state;
	private String     tagText;

	// прочитанные данные            
	private String     messageId;
	private String     responceTag;

	private ContentHandler bodyHandler;
	private GorodErrorMessageHandler errorHandler;

	private int bodyLevel;

	public GorodResponseHandler(MessageInfo messageInfo)
	{
		reset();
		this.bodyHandler = new ResponseBodyHandler(messageInfo.getResponses());
	}

	/**
	 * ќбнулить состо€ние парсера. ѕосле этого он снова готов к работе
	 */
	public void reset()
	{
		state        = State.messageTag;
		resultType   = null;
		tagText      = null;
		errorHandler = new GorodErrorMessageHandler();
		bodyLevel    = -1;
		messageId    = null;
		responceTag  = null;
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

		if (errorHandler != null)
		{
		    if ((errorHandler.getErrorCode()!=null)&&(errorHandler.getErrorCode().equals(CARD_NOTFOUND_CODE)))
		        errorHandler.throwCardNotFoundException();
			else
				errorHandler.throwException();
		}

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
				messageId = new RandomGUID().getStringValue();
				state = State.startBody;
				break;
			case startBody:
				initBodyProcessing(qName);
			case continueBody:
				if(qName.equals("Code"))
				{
					state = State.codeTag;
					errorHandler.startDocument();
					errorHandler.startElement(uri, localName, qName, attributes);
					break;
				}
				bodyLevel++;
				bodyHandler.startElement(uri, localName, qName, attributes);
				break;
			case codeTag:
				state = State.errorCodeTag;
				errorHandler.startElement(uri, localName, qName, attributes);
				break;
			case errorCodeTag:
				errorHandler.startElement(uri, localName, qName, attributes);
				state = State.errorDdescriptionTag;
				break;
			case errorDdescriptionTag:
				errorHandler.startElement(uri, localName, qName, attributes);
				break;
			default:
				throw new SAXException("Ѕольше ничего не ждем");
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
		    case errorCodeTag:
			    errorHandler.endElement(uri, localName, qName);
			    if(resultType == ResultType.error)
			        state = State.errorDdescriptionTag;
			    else
			        state = State.codeTag;
			    break;
		    case errorDdescriptionTag:
			    state = State.codeTag;
			    errorHandler.endElement(uri, localName, qName);
			    break;
			case codeTag:
				state = State.continueBody;
				errorHandler.endDocument();
				if(resultType == ResultType.body)
					errorHandler = null;
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
			case errorCodeTag:
				String errCode = new String(ch,start, length);
				errorHandler.characters(ch, start, length);
				if(errCode.equals("0"))
					resultType = ResultType.body;
				else
					resultType = ResultType.error;
				break;
			case errorDdescriptionTag:
				errorHandler.characters(ch, start, length);
                tagText = new String(ch, start, length);
				break;
			case continueBody:
				bodyHandler.characters(ch, start, length);
				break;
			default:
				tagText = new String(ch, start, length);
		}
	}
}
