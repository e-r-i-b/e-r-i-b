package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import org.xml.sax.SAXException;

/**
 * @author Gulov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� � ������ � ��� ��������� ����
 */
public class MessageLogger
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final TextMessage message;
	private Application application;
	private String messageType;

	public MessageLogger(TextMessage message, Application application, String messageType)
	{
		this.message = message;
		this.application = application;
		this.messageType = messageType;
	}

	public void makeAndWrite() throws RuntimeException
	{
		MessagingLogEntry logEntry = make(application);
		if (logEntry != null)
			write(MessageLogService.getMessageLogWriter(), logEntry);
	}

	private MessagingLogEntry make(Application application)
	{
		MessagingLogEntry result = MessageLogService.createLogEntry();
		result.setApplication(application);
		result.setSystem(com.rssl.phizic.logging.messaging.System.ERMB_SOS);

		try
		{
			MessageParser messageParser = new MessageParser();
			RequestValues values = messageParser.execute(message.getText());
			result.setMessageRequestId(values.getUID());
			result.setMessageType(messageType);
			result.setMessageRequest(message.getTextToLog());
			result.setExecutionTime(values.getTime());
			return result;
		}
		catch (SAXException e)
		{
			log.error("������ ������� ��������� ��� ������ � ������:\n" + message.getText(), e);
		}
		return null;
	}

	private void write(MessageLogWriter writer, MessagingLogEntry logEntry)
	{
		try
		{
			//TODO: (����) ��������� ������ � ������� - loginId, personId... � LogThreadContext. ����������� ����� �.
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("������ ��� ������ � ������ ���������", e);
		}
	}
}
