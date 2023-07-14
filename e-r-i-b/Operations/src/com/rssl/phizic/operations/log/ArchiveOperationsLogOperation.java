package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.LogEntry;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.Date;

/**
 * @author gladishev
 * @ created 02.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveOperationsLogOperation extends ArchiveLogOperationBase
{

	public static final String LOGIN_TAG_NAME = "login-id";
	public static final String APPLICATION_TAG_NAME = "application";
	public static final String DATE_TAG_NAME = "date";
	public static final String SESSION_TAG_NAME = "session-id";
	public static final String IP_ADDRESS_TAG_NAME = "ip-address";
	public static final String TYPE_TAG_NAME = "type";
	public static final String DESCRIPTION_TAG_NAME = "description";
	public static final String PARAMETERS_TAG_NAME = "parameters";
	public static final String OPERATION_TAG_NAME = "operation-key";
	public static final String DESCRIPTION_KEY_TAG_NAME = "description-key";
	public static final String EXECUTION_TIME_TAG_NAME = "execution-time";

	protected String getSettingPrefix()
	{
		return Constants.USER_LOG_PREFIX;
	}

	void createRecord(XMLSerializer serializer, Object element) throws SAXException
	{
		serializer.startElement(RECORD_TAG_NAME, null);

		LogEntry logEntry = (LogEntry) element;

		serializer.startElement(LOGIN_TAG_NAME, null);
		String loginId = String.valueOf(logEntry.getLoginId());
		serializer.characters(loginId.toCharArray(), 0, loginId.length());
		serializer.endElement(LOGIN_TAG_NAME);

		serializer.startElement(APPLICATION_TAG_NAME, null);
		String application = emptyValueSafeGet(logEntry.getApplication() == null ? null : logEntry.getApplication().name());
		serializer.characters(application.toCharArray(), 0, application.length());
		serializer.endElement(APPLICATION_TAG_NAME);

		serializer.startElement(OPERATION_TAG_NAME, null);
		String operationKey = emptyValueSafeGet(logEntry.getOperationKey());
		serializer.characters(operationKey.toCharArray(), 0, operationKey.length());
		serializer.endElement(OPERATION_TAG_NAME);

		serializer.startElement(DATE_TAG_NAME, null);
		String date = String.format(DATE_FORMAT_STRING + " " + TIME_FORMAT_STRING, DateHelper.toDate(logEntry.getDate()));
		serializer.characters(date.toCharArray(), 0, date.length());
		serializer.endElement(DATE_TAG_NAME);

		serializer.startElement(DESCRIPTION_TAG_NAME, null);
		String description = emptyValueSafeGet(logEntry.getDescription());
		serializer.characters(description.toCharArray(), 0, description.length());
		serializer.endElement(DESCRIPTION_TAG_NAME);

		serializer.startElement(PARAMETERS_TAG_NAME, null);
		String parameters = emptyValueSafeGet(XmlHelper.escape(logEntry.getParameters()));
		serializer.characters(parameters.toCharArray(), 0, parameters.length());
		serializer.endElement(PARAMETERS_TAG_NAME);

		serializer.startElement(IP_ADDRESS_TAG_NAME, null);
		String ipAddress = emptyValueSafeGet(logEntry.getIpAddress());
		serializer.characters(ipAddress.toCharArray(), 0, ipAddress.length());
		serializer.endElement(IP_ADDRESS_TAG_NAME);

		serializer.startElement(SESSION_TAG_NAME, null);
		String sessionId = emptyValueSafeGet(logEntry.getSessionId());
		serializer.characters(sessionId.toCharArray(), 0, sessionId.length());
		serializer.endElement(SESSION_TAG_NAME);

		serializer.startElement(TYPE_TAG_NAME, null);
		String type = emptyValueSafeGet(logEntry.getType());
		serializer.characters(type.toCharArray(), 0, type.length());
		serializer.endElement(TYPE_TAG_NAME);

		serializer.startElement(DESCRIPTION_KEY_TAG_NAME, null);
		String key = emptyValueSafeGet(logEntry.getKey());
		serializer.characters(key.toCharArray(), 0, key.length());
		serializer.endElement(DESCRIPTION_KEY_TAG_NAME);

		serializer.startElement(EXECUTION_TIME_TAG_NAME, null);
		String time = String.valueOf(logEntry.getExecutionTime());
		serializer.characters(time.toCharArray(), 0, time.length());
		serializer.endElement(EXECUTION_TIME_TAG_NAME);

		serializer.startElement(APP_SERVER_INFO, null);
		String addInfo = String.valueOf(logEntry.getAddInfo());
		serializer.characters(addInfo.toCharArray(), 0, addInfo.length());
		serializer.endElement(APP_SERVER_INFO);


		serializer.endElement(RECORD_TAG_NAME);
	}

	protected DefaultHandler getHandler()
	{
		return new DefaultHandler()
		{
			private LogEntry logEntry;
			private String tagText;

			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
			{
				if (RECORD_TAG_NAME.equals(qName))
				{
					if (logEntry != null)
					{
						throw new IllegalStateException();
					}
					logEntry = new LogEntry();
				}
				tagText = "";
			}

			public void endElement(String uri, String localName, String qName) throws SAXException
			{
				try
				{
					if (LOGIN_TAG_NAME.equals(qName))
					{
						logEntry.setLoginId(Long.parseLong(tagText));
					}
					else if (APPLICATION_TAG_NAME.equals(qName))
					{
						logEntry.setApplication(Application.valueOf(tagText));
					}
					else if (DATE_TAG_NAME.equals(qName))
					{
						Date date = DateHelper.parseStringTime(tagText);
						logEntry.setDate(DateHelper.toCalendar(date));
					}
					else if (SESSION_TAG_NAME.equals(qName))
					{
						logEntry.setSessionId(tagText);
					}
					else if (IP_ADDRESS_TAG_NAME.equals(qName))
					{
						logEntry.setIpAddress(tagText);
					}
					else if (OPERATION_TAG_NAME.equals(qName))
					{
						logEntry.setOperationKey(tagText);
					}
					else if (DESCRIPTION_TAG_NAME.equals(qName))
					{
						logEntry.setDescription(tagText);
					}
					else if (PARAMETERS_TAG_NAME.equals(qName))
					{
						logEntry.setParameters(tagText);
					}
					else if (TYPE_TAG_NAME.equals(qName))
					{
						logEntry.setType(tagText);
					}
					else if (DESCRIPTION_KEY_TAG_NAME.equals(qName))
					{
						logEntry.setKey(tagText);
					}
					else if (EXECUTION_TIME_TAG_NAME.equals(qName))
					{
						logEntry.setExecutionTime(Long.parseLong(tagText));
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
		return Constants.USER_LOG_ARCHIVE_NAME;
	}
}
