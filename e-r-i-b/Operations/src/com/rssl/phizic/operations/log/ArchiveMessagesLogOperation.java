package com.rssl.phizic.operations.log;

import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.Date;


/**
 * @author gladishev
 * @ created 01.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveMessagesLogOperation extends ArchiveLogOperationBase
{
	public static final String DATE_TAG_NAME = "date";
	public static final String APPLICATION_TAG_NAME = "application";
	public static final String EXECUTION_TIME_TAG_NAME = "execution-time";
	public static final String MESSAGE_TYPE_TAG_NAME = "message-type";
	private static final String MESSAGE_REQUEST_ID_TAG_NAME = "message-request-id";
	public static final String MESSAGE_REQUEST_TAG_NAME = "message-request";
	public static final String MESSAGE_RESPONSE_ID_TAG_NAME = "message-response-id";
	public static final String MESSAGE_RESPONSE_TAG_NAME = "message-response";
	public static final String SYSTEM_TAG_NAME = "system";
	public static final String LOGIN_ID_TAG_NAME = "login-id";

	protected String getSettingPrefix()
	{
		return Constants.MESSAGE_LOG_PREFIX;
	}

	void createRecord(XMLSerializer serializer, Object element) throws SAXException
	{
		MessagingLogEntry logEntry = (MessagingLogEntry) element;

		serializer.startElement(RECORD_TAG_NAME, null);

		serializer.startElement(DATE_TAG_NAME, null);
		String date = String.format(DATE_FORMAT_STRING + " " + TIME_FORMAT_STRING, DateHelper.toDate(logEntry.getDate()));
		serializer.characters(date.toCharArray(), 0, date.length());
		serializer.endElement(DATE_TAG_NAME);

		serializer.startElement(EXECUTION_TIME_TAG_NAME, null);
		//ХАК, тк до некоторого времени не вставлялись время записи в ритейл.
		Long time = logEntry.getExecutionTime();
		if (time == null)
		{
			time = 0L;
		}
		char[] executionTime = String.valueOf(time).toCharArray();
		serializer.characters(executionTime, 0, executionTime.length);
		serializer.endElement(EXECUTION_TIME_TAG_NAME);

		serializer.startElement(APPLICATION_TAG_NAME, null);
		String application = emptyValueSafeGet(logEntry.getApplication() == null ? null : logEntry.getApplication().name());
		serializer.characters(application.toCharArray(), 0, application.length());
		serializer.endElement(APPLICATION_TAG_NAME);

		serializer.startElement(MESSAGE_TYPE_TAG_NAME, null);
		String messageType = emptyValueSafeGet(logEntry.getMessageType());
		serializer.characters(messageType.toCharArray(), 0, messageType.length());
		serializer.endElement(MESSAGE_TYPE_TAG_NAME);

		serializer.startElement(MESSAGE_REQUEST_ID_TAG_NAME, null);
		String messageRequestId = emptyValueSafeGet(logEntry.getMessageRequestId());
		serializer.characters(messageRequestId.toCharArray(), 0, messageRequestId.length());
		serializer.endElement(MESSAGE_REQUEST_ID_TAG_NAME);

		serializer.startElement(MESSAGE_REQUEST_TAG_NAME, null);
		String messageRequest = emptyValueSafeGet(logEntry.getMessageRequest());
		serializer.characters(messageRequest.toCharArray(), 0, messageRequest.length());
		serializer.endElement(MESSAGE_REQUEST_TAG_NAME);

		serializer.startElement(MESSAGE_RESPONSE_ID_TAG_NAME, null);
		String messageResponseId = emptyValueSafeGet(logEntry.getMessageResponseId());
		serializer.characters(messageResponseId.toCharArray(), 0, messageResponseId.length());
		serializer.endElement(MESSAGE_RESPONSE_ID_TAG_NAME);

		serializer.startElement(MESSAGE_RESPONSE_TAG_NAME, null);
		String messageResponse = emptyValueSafeGet(logEntry.getMessageResponse());
		serializer.characters(messageResponse.toCharArray(), 0, messageResponse.length());
		serializer.endElement(MESSAGE_RESPONSE_TAG_NAME);

		serializer.startElement(SYSTEM_TAG_NAME, null);
		String system = emptyValueSafeGet(logEntry.getSystem()==null?null:logEntry.getSystem().toValue());
		serializer.characters(system.toCharArray(), 0, system.length());
		serializer.endElement(SYSTEM_TAG_NAME);

		Long loginId = logEntry.getLoginId();
		String loginIdStr = loginId==null?"":String.valueOf(loginId);
		char[] chars = loginIdStr.toCharArray();
		serializer.startElement(LOGIN_ID_TAG_NAME, null);
		serializer.characters(chars, 0, chars.length);
		serializer.endElement(LOGIN_ID_TAG_NAME);

		serializer.endElement(RECORD_TAG_NAME);
	}

	protected DefaultHandler getHandler()
	{
		return new DefaultHandler()
		{
			private MessagingLogEntry logEntry;
			private String tagText;

			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
			{
				if (RECORD_TAG_NAME.equals(qName))
				{
					if (logEntry != null)
					{
						throw new IllegalStateException();
					}
					logEntry = MessageLogService.createLogEntry();
				}
				tagText = "";
			}

			public void endElement(String uri, String localName, String qName) throws SAXException
			{
				try
				{

					if (DATE_TAG_NAME.equals(qName))
					{
						Date date = DateHelper.parseStringTime(tagText);
						logEntry.setDate(DateHelper.toCalendar(date));
					}
					else if (APPLICATION_TAG_NAME.equals(qName))
					{
						logEntry.setApplication(Application.valueOf(tagText));
					}
					else if (EXECUTION_TIME_TAG_NAME.equals(qName))
					{
						logEntry.setExecutionTime(Long.parseLong(tagText));
					}
					else if (MESSAGE_TYPE_TAG_NAME.equals(qName))
					{
						logEntry.setMessageType(tagText);
					}
					else if (MESSAGE_REQUEST_ID_TAG_NAME.equals(qName))
					{
						logEntry.setMessageRequestId(tagText);
					}
					else if (MESSAGE_REQUEST_TAG_NAME.equals(qName))
					{
						logEntry.setMessageRequest(tagText);
					}
					else if (MESSAGE_RESPONSE_ID_TAG_NAME.equals(qName))
					{
						logEntry.setMessageResponseId(tagText);
					}
					else if (MESSAGE_RESPONSE_TAG_NAME.equals(qName))
					{
						logEntry.setMessageResponse(tagText);
					}
					else if (SYSTEM_TAG_NAME.equals(qName))
					{
						logEntry.setSystem(System.fromValue(tagText));
					}
					else if (LOGIN_ID_TAG_NAME.equals(qName))
					{
						//Ранее пустые login_id сохранялись в файле со значением "null", приходится проверять.
						logEntry.setLoginId(StringHelper.isEmpty(tagText) || "null".equals(tagText)?null:Long.parseLong(tagText));
					}
					else if (RECORD_TAG_NAME.equals(qName))
					{
						insertRecord(logEntry);
						logEntry = null;
					}
				}
				catch (ParseException e)
				{
					throw new SAXException(e);
				}
				catch (BusinessException e)
				{
					throw new SAXException(e);
				}
			}

			public void characters(char ch[], int start, int length) throws SAXException
			{
				tagText += new String(ch, start, length);
			}
		};
	}

	protected String getLogArchiveName()
	{
		return Constants.MESSAGE_LOG_ARCHIVE_NAME;
	}
}
