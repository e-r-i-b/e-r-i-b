package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.logging.system.LogLevel;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.DateHelper;
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
public class ArchiveSystemLogOperation extends ArchiveLogOperationBase
{
	public static final String LEVEL_TAG_NAME = "message-level";
	public static final String DATE_TAG_NAME = "date";
	public static final String LOGIN_TAG_NAME = "login-id";
	public static final String APPLICATION_TAG_NAME = "application";
	public static final String MESSAGE_TAG_NAME = "message";
	public static final String SESSION_TAG_NAME = "session-id";
	public static final String IP_ADDRESS_TAG_NAME = "ip-address";
	public static final String SOURCE_TAG_NAME = "source";

	protected String getSettingPrefix()
	{
		return Constants.SYSTEM_LOG_PREFIX;
	}

	void createRecord(XMLSerializer serializer, Object element) throws SAXException
	{
		SystemLogEntry logEntry = (SystemLogEntry) element;

		serializer.startElement(RECORD_TAG_NAME, null);

		serializer.startElement(LEVEL_TAG_NAME, null);
		String level = emptyValueSafeGet(logEntry.getLevel()==null?null:logEntry.getLevel().toValue());
		serializer.characters(level.toCharArray(), 0, level.length());
		serializer.endElement(LEVEL_TAG_NAME);

		serializer.startElement(DATE_TAG_NAME, null);
		String date = String.format(DATE_FORMAT_STRING + " " + TIME_FORMAT_STRING, DateHelper.toDate(logEntry.getDate()));
		serializer.characters(date.toCharArray(), 0, date.length());
		serializer.endElement(DATE_TAG_NAME);

		serializer.startElement(LOGIN_TAG_NAME, null);
		String loginId = String.valueOf(logEntry.getLoginId());
		serializer.characters(loginId.toCharArray(), 0, loginId.length());
		serializer.endElement(LOGIN_TAG_NAME);

		serializer.startElement(APPLICATION_TAG_NAME, null);
		String application = emptyValueSafeGet(logEntry.getApplication() == null ? null : logEntry.getApplication().name());
		serializer.characters(application.toCharArray(), 0, application.length());
		serializer.endElement(APPLICATION_TAG_NAME);

		serializer.startElement(MESSAGE_TAG_NAME, null);
		String message = emptyValueSafeGet(logEntry.getMessage());
		serializer.startCDATA();
		serializer.characters(message.toCharArray(), 0, message.length());
		serializer.endCDATA();
		serializer.endElement(MESSAGE_TAG_NAME);

		serializer.startElement(SESSION_TAG_NAME, null);
		String sessionId = emptyValueSafeGet(logEntry.getSessionId());
		serializer.characters(sessionId.toCharArray(), 0, sessionId.length());
		serializer.endElement(SESSION_TAG_NAME);

		serializer.startElement(IP_ADDRESS_TAG_NAME, null);
		String ipAddress = emptyValueSafeGet(logEntry.getIpAddress());
		serializer.characters(ipAddress.toCharArray(), 0, ipAddress.length());
		serializer.endElement(IP_ADDRESS_TAG_NAME);

		serializer.startElement(SOURCE_TAG_NAME, null);
		String source = emptyValueSafeGet(logEntry.getSource()==null?null:logEntry.getSource().toValue());
		serializer.characters(source.toCharArray(), 0, source.length());
		serializer.endElement(SOURCE_TAG_NAME);

		serializer.endElement(RECORD_TAG_NAME);
	}

	protected DefaultHandler getHandler()
	{
		return new DefaultHandler()
		{
			private SystemLogEntry logEntry;
			private String tagText;

			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
			{
				if (RECORD_TAG_NAME.equals(qName))
				{
					if (logEntry != null)
					{
						throw new IllegalStateException();
					}
					logEntry = new SystemLogEntry();
				}
				tagText = "";
			}

			public void endElement(String uri, String localName, String qName) throws SAXException
			{
				try
				{
					if (LEVEL_TAG_NAME.equals(qName))
					{
						logEntry.setLevel(LogLevel.fromValue(tagText));
					}
					else if (DATE_TAG_NAME.equals(qName))
					{
						Date date = DateHelper.parseStringTime(tagText);
						logEntry.setDate(DateHelper.toCalendar(date));
					}
					else if (LOGIN_TAG_NAME.equals(qName))
					{
						logEntry.setLoginId(Long.parseLong(tagText));
					}
					else if (APPLICATION_TAG_NAME.equals(qName))
					{
						logEntry.setApplication(Application.valueOf(tagText));
					}
					else if (MESSAGE_TAG_NAME.equals(qName))
					{
						logEntry.setMessage(tagText);
					}
					else if (SESSION_TAG_NAME.equals(qName))
					{
						logEntry.setSessionId(tagText);
					}
					else if (IP_ADDRESS_TAG_NAME.equals(qName))
					{
						logEntry.setIpAddress(tagText);
					}
					else if (APP_SERVER_INFO.equals(qName))
					{
						logEntry.setAddInfo(LogThreadContext.getAppServerInfo());
					}
					else if (RECORD_TAG_NAME.equals(qName))
					{
						insertRecord(logEntry);
						logEntry = null;
					}
					else if (SOURCE_TAG_NAME.equals(qName))
					{
						logEntry.setSource(LogModule.fromValue(tagText));
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
		return Constants.SYSTEM_LOG_ARCHIVE_NAME;
	}

}
