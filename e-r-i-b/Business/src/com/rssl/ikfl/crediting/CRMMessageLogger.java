package com.rssl.ikfl.crediting;

import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;

import static com.rssl.phizic.logging.messaging.System.creditcrm;

/**
 * @author Erkin
 * @ created 03.01.2015
 * @ $Author$
 * @ $Revision$
 */
public class CRMMessageLogger
{
	private final Log syslog = PhizICLogFactory.getLog(LogModule.Gate);

	private final MessageLogWriter msglog = MessageLogService.getMessageLogWriter();

	public void logMessage(CRMMessage message)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(creditcrm);
			logEntry.setMessageRequestId(message.getRequestUID());
			logEntry.setMessageType(message.getRequestClass().getSimpleName());
			logEntry.setMessageRequest(message.getMessage());
			logEntry.setDate(message.getRequestTime());
			msglog.write(logEntry);
		}
		catch (Exception e)
		{
			syslog.error("Неудачная попытка записи в Журнал Сообщений", e);
		}
	}
}
