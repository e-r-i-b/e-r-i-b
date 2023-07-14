package com.rssl.phizic.ejbtest.service;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;

import javax.jms.JMSException;

/**
 *
 * @author bogdanov
 * @ created 06.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class JMSClaimTestSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * Отправляет документ
	 * @param rqUid uuid.
	 * @param message сообщение.
	 */
	public final void send(String rqUid, String message) throws Exception
	{
		String requestXMLString = message;
		String requestID = rqUid;
		String messageType = getMesType(message);

		try
		{
			JMSTransportTestProvider.getInstance().doRequest(requestXMLString, false);
		}
		catch (JMSException e)
		{
			throw new Exception("Сбой на отправке заявки в очередь. ESB", e);
		}
		finally
		{
			// Логирование заявки в Журнал сообщений
			writeMessageLog(requestXMLString, requestID, messageType);
		}

	}

	private String getMesType(String message)
	{
		if (message.contains("etPrivateClient"))
			return "GetPrivateClientRs";
		if (message.contains("oncludeEDBO"))
			return "ConcludeEDBORs";
		if (message.contains("reateCardContract"))
			return "CreateCardContractRs";
		if (message.contains("ssueCard"))
			return "IssueCardRs";

		return "unknown";
	}

	private void writeMessageLog(String requestXMLString, String requestID, String messageType)
	{
		if (requestXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(System.WebTest);
			logEntry.setMessageRequestId(requestID);
			logEntry.setMessageType(messageType);
			logEntry.setMessageRequest(requestXMLString);
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Проблемы с записью в журнал сообщений", e);
		}
	}


}
