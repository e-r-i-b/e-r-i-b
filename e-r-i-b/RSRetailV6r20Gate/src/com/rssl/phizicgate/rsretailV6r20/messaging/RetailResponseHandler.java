package com.rssl.phizicgate.rsretailV6r20.messaging;

import com.rssl.phizic.ConfigurationCheckedException;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorMessagesMatcher;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorType;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.ResponseBodyHandler;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 09.03.2007
 * @ $Author: omeliyanchuk $
 * @ $Revision: 4391 $
 */

public class RetailResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String MESSAGE_TAG = "response";
	private static final String MESSAGE_PARENT_ID_TAG = "parentId";
	private static final String MESSAGE_ID_TAG = "id";
	private static final String ERROR_CODE_TAG = "errorCode";
	private static final String ERROR_TEXT_TAG = "errorText";
	private static final String OK_RESULT = "0";

	private enum State
	{
		messageTag,
		messageParentIdTag,
		messageIdTag,
		errorCodeTag,
		errorBody,
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
	private State state;
	private String tagText;

	// прочитанные данные
	private String messageId;
	private String messageParentId;
	private String responceTag;
	private String errorCode;
	private String errorText;

	private ContentHandler bodyHandler;
	private Map<String, String> attributes;

	private int bodyLevel;

	public RetailResponseHandler(MessageInfo messageInfo)
	{
		reset();
		this.bodyHandler = new ResponseBodyHandler(messageInfo.getResponses());
		this.attributes = messageInfo.getAttributes();
	}

	/**
	 * Обнулить состояние парсера. После этого он снова готов к работе
	 */
	public void reset()
	{
		state = State.messageTag;
		resultType = null;
		tagText = null;
		bodyLevel = -1;
		messageId = null;
		messageParentId = null;
		responceTag = null;
		errorCode = null;
		errorText = null;
	}

	public String getResponceTag()
	{
		return responceTag;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public String getMessageParentId()
	{
		return messageParentId;
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

	public Map<String, String> getAttributes()
	{
		return attributes;
	}

	public boolean needParseError()
	{
		if (!StringHelper.isEmpty(attributes.get("needParseError")))
			return Boolean.parseBoolean(attributes.get("needParseError"));
		return true;
	}

	public void throwException()
			throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
		if (errorText != null)
			;
		errorProcess();
	}

	private void errorProcess()
			throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
		try
		{
			ErrorMessagesMatcher matcher = ErrorMessagesMatcher.getInstance();
			List<ErrorMessage> errorMessages = matcher.matchMessage(errorCode, ErrorSystem.Retail);
			if (errorMessages.size() != 0)
			{
				ErrorMessage errorMessage = errorMessages.get(0);
				if (errorMessage.getErrorType().equals(ErrorType.Client))
					throw new GateMessagingClientException(errorMessage.getMessage());
				if (errorMessage.getErrorType().equals(ErrorType.Validation))
					throw new GateMessagingValidationException(errorMessage);
			}
			throw new GateMessagingException(errorText, errorCode, errorText);
		}
		catch (ConfigurationCheckedException e)
		{
			throw new RuntimeException(e);
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
				assertTag(qName, RetailResponseHandler.MESSAGE_TAG);
				state = State.messageParentIdTag;
				break;
			case messageParentIdTag:
				assertTag(qName, RetailResponseHandler.MESSAGE_PARENT_ID_TAG);
				break;
			case messageIdTag:
				assertTag(qName, RetailResponseHandler.MESSAGE_ID_TAG);
				break;
			case errorBody:
				assertTag(qName, RetailResponseHandler.ERROR_TEXT_TAG);
				continueBody(uri, localName, qName, attributes);
				break;
			case startBody:
				resultType = ResultType.body;
				state = State.continueBody;
				bodyLevel = 0;
				bodyHandler.startDocument();
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
		if (ERROR_CODE_TAG.equals(qName))
		{
			state = State.errorCodeTag;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case messageParentIdTag:
				messageParentId = tagText;
				state = State.messageIdTag;
				break;
			case messageIdTag:
				messageId = tagText;
				state = State.startBody;
				break;
			case errorCodeTag:
				errorCode = tagText;
				processError();
				continueBody(uri, localName, qName);
				break;
			case errorBody:
				errorText = tagText;
				state = State.continueBody;
				continueBody(uri, localName, qName);
				break;
			case continueBody:
				continueBody(uri, localName, qName);
				break;
			default:
				// do nothing
		}
	}

	private void continueBody(String uri, String localName, String qName) throws SAXException
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

	private void processError()
	{
		if (!needParseError())
		{
			state = State.continueBody;
			return;
		}
		else
		{
			if (OK_RESULT.equals(errorCode))
			{
				state = State.continueBody;
				return;
			}
			resultType = ResultType.error;
			state = State.errorBody;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		switch (state)
		{
			case continueBody:
				bodyHandler.characters(ch, start, length);
				break;
			case errorBody:
			case errorCodeTag:
				bodyHandler.characters(ch, start, length);
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
}