package com.rssl.phizic.gate.messaging.impl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.rssl.phizic.gate.messaging.*;

/**
 * Обработчик ошибок
 *
 * структура сообщения
 *
 * <br/>&lt;error_a&gt;
 *    <br/>&nbsp;&nbsp;&lt;code&gt;[ERROR_UNIQUE | ERROR_VALIDATE | ERROR_CLIENT]&lt;/code&gt;
 *    <br/>&nbsp;&nbsp;&lt;message&gt;Сообщение...&lt;/message&gt;
 * <br/>&lt;/error_a&gt;
 *
 * <br/><br/>где code:
 *  <br/>&nbsp;ERROR_UNIQUE - используется в случае отказа в исполнении документа <br/>&nbsp;по причине дублирования уникального идентификатора
 *  <br/>&nbsp;ERROR_VALIDATE - используется в тех случаях, когда отказ свидетельствует <br/>&nbsp;о некорректной работе одной из систем, и недопустимо сообщать причину ошибки из этого отказа на рабочее место клиента/пользователя
 *  <br/>&nbsp;ERROR_CLIENT - используется в тех случаях, когда причина отказа может <br/>&nbsp;быть сообщена на рабочее место клиента/пользователя
 * <br/>&nbsp;ERROR_SHORT_MONEY - используется в тех случаях, когда для оплаты обсуживания не хватает денег
 *
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: krenev_a $
 * @ $Revision: 69122 $
 */
public class DefaultErrorMessageHandler extends DefaultHandler
{
	public static final String ERROR_UNIQUE_CODE   = "ERROR_UNIQUE";
	public static final String ERROR_VALIDATE_CODE = "ERROR_VALIDATE";
	public static final String ERROR_CLIENT_CODE = "ERROR_CLIENT";
	public static final String ERROR_SHORT_MONEY = "ERROR_SHORT_MONEY";
	public static final String UNKNOWN_UID_CODE = "UNKNOWN_UID";

	private static final String CODE_TAG    = "code";
	private static final String MESSAGE_TAG = "message";

	protected String getCodeTag()
	{
		return CODE_TAG;
	}

	protected String getMessageTag()
	{
		return MESSAGE_TAG;
	}
	private enum State
	{
		start,
		code,
		message,
		end
	}

	private State state = State.start;

	public String getErrorCode()
	{
		return errorCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	protected String    errorCode;
	protected String    errorMessage;
	protected Exception exception;

	private String tagText;

	public void throwException() throws GateMessagingException, GateMessagingClientException, GateMessagingValidationException
	{
        if ( exception instanceof GateMessagingException)
        {
            throw (GateMessagingException) exception;

        }
        else if ( exception instanceof GateMessagingClientException)
        {
            throw (GateMessagingClientException) exception;
        }
        else if ( exception instanceof GateMessagingValidationException)
        {
            throw (GateMessagingValidationException) exception;
        }
    }

	public void endDocument() throws SAXException
	{
		if(errorCode.equals(ERROR_UNIQUE_CODE))
		{
			exception = new NonUniqueMessageIdException(errorMessage);
		}
		else if (errorCode.equals(ERROR_VALIDATE_CODE))
		{
			exception = new GateMessagingValidationException(errorMessage);
		}
		else if (errorCode.equals(ERROR_CLIENT_CODE) || errorCode.equals(UNKNOWN_UID_CODE))
		{
			exception = new GateMessagingClientException(errorMessage);
		}
		else
		{
			if(errorCode!=null && errorMessage!=null)
				exception = new GateMessagingException(errorMessage, errorCode, errorMessage);
			else
				exception = new GateMessagingException(errorMessage);
		}
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		switch(state)
		{
			case start:
				state = State.code;
				break;
			case code:
				assertTag(qName, getCodeTag());
				break;
			case message:
				assertTag(qName, getMessageTag());
				break;

		}
		tagText = "";
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case code:
				errorCode = tagText;
				state = State.message;
				break;
			case message:
				errorMessage = tagText;
				state = State.end;
				break;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		tagText += new String(ch, start, length);
	}

	private void assertTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ожидался тег " + tag);
		}
	}
}