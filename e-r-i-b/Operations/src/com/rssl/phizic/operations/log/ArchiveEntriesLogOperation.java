package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.logon.LogonLogEntry;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.Date;

/**
 * @author komarov
 * @ created 11.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ArchiveEntriesLogOperation extends ArchiveLogOperationBase
{
	public static final String LOGIN_ID_TAG_NAME = "login-id";
	public static final String APPLICATION_TAG_NAME = "application";
	public static final String SESSION_ID_TAG_NAME = "session-id";
	public static final String LOGIN_DATE_TAG_NAME = "login-date";


	protected String getSettingPrefix()
	{
		return Constants.ENTRIES_LOG_PREFIX;  //To change body of implemented methods use File | Settings | File Templates.
	}

	void createRecord(XMLSerializer serializer, Object element) throws SAXException
	{
		serializer.startElement(RECORD_TAG_NAME, null);

		LogonLogEntry logEntry = (LogonLogEntry) element;

		serializer.startElement(LOGIN_ID_TAG_NAME, null);
		String loginId = String.valueOf(logEntry.getLoginId());
		serializer.characters(loginId.toCharArray(), 0, loginId.length());
		serializer.endElement(LOGIN_ID_TAG_NAME);

		serializer.startElement(APPLICATION_TAG_NAME, null);
		String application = emptyValueSafeGet(logEntry.getApplication()==null?null:logEntry.getApplication().name());
		serializer.characters(application.toCharArray(), 0, application.length());
		serializer.endElement(APPLICATION_TAG_NAME);

		serializer.startElement(LOGIN_DATE_TAG_NAME, null);
		String date = String.format(DATE_FORMAT_STRING + " " + TIME_FORMAT_STRING, DateHelper.toDate(logEntry.getDate()));
		serializer.characters(date.toCharArray(), 0, date.length());
		serializer.endElement(LOGIN_DATE_TAG_NAME);

		serializer.startElement(SESSION_ID_TAG_NAME, null);
		String sessionId = emptyValueSafeGet(logEntry.getSessionId());
		serializer.characters(sessionId.toCharArray(), 0, sessionId.length());
		serializer.endElement(SESSION_ID_TAG_NAME);

		serializer.endElement(RECORD_TAG_NAME);
	}

	protected DefaultHandler getHandler()
	{
		return new DefaultHandler()
		{
			private LogonLogEntry logEntry;
			private String tagText;

			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
			{
				if (RECORD_TAG_NAME.equals(qName))
				{
					if (logEntry != null)
					{
						throw new IllegalStateException();
					}
					logEntry = new LogonLogEntry();
				}
				tagText = "";
			}

			public void endElement(String uri, String localName, String qName) throws SAXException
			{
				try
				{

					if (LOGIN_DATE_TAG_NAME.equals(qName))
					{
						Date date = DateHelper.parseStringTime(tagText);
						logEntry.setDate(DateHelper.toCalendar(date));
					}
					else if (APPLICATION_TAG_NAME.equals(qName))
					{
						logEntry.setApplication(Application.valueOf(tagText));
					}

					else if (LOGIN_ID_TAG_NAME.equals(qName))
					{
						//Ранее пустые login_id сохранялись в файле со значением "null", приходится проверять.
						logEntry.setLoginId(StringHelper.isEmpty(tagText) || "null".equals(tagText)?null:Long.parseLong(tagText));
					}
					else if(SESSION_ID_TAG_NAME.equals(qName))
					{
						logEntry.setSessionId(tagText);
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
		return Constants.ENTRIES_LOG_ARCHIVE_NAME;
	}
}
