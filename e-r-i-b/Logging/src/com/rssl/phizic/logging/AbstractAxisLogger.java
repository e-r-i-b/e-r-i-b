package com.rssl.phizic.logging;

import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;

import java.util.Calendar;
import javax.xml.soap.SOAPBody;

/**
 * @author Erkin
 * @ created 19.07.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Логгер для журналировани сообщений аксиса
 */
public abstract class AbstractAxisLogger extends BasicHandler
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final System systemId;

	///////////////////////////////////////////////////////////////////////////

	public AbstractAxisLogger(System systemId)
	{
		if (systemId == null)
			throw new IllegalArgumentException("Argument 'systemId' cannot be null nor empty");
		this.systemId = systemId;
	}

	protected System getSystemId()
	{
		return systemId;
	}

	public final void invoke(MessageContext msgContext) throws AxisFault
	{
		if (msgContext == null)
			return;
		if (!use())
			return;

		Message request = msgContext.getRequestMessage();
		Message response = msgContext.getResponseMessage();
		Message currentMessage = msgContext.getCurrentMessage();

		// A. Обработка запроса
		if (currentMessage == request)
			msgContext.setProperty("start-time", Calendar.getInstance());

			// B. Обработка ответа
		else if (currentMessage == response)
			writeToLog(msgContext);
	}

	protected void writeToLog(MessageContext msgContext)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			Message request = msgContext.getRequestMessage();
			Message response = msgContext.getResponseMessage();
			SOAPBody requestBody = AxisLoggerHelper.getBody(request);
			SOAPBody responseBody = AxisLoggerHelper.getBody(response);

			logEntry.setSystem(systemId);

			logEntry.setMessageRequestId(getMessageId(requestBody));
			logEntry.setMessageType(getRequestMessageType(requestBody));
			logEntry.setMessageRequest(getMessageRequest(request));
			logEntry.setMessageResponseId(getMessageId(responseBody));
			logEntry.setMessageResponse(getMessageResponse(response));
			logEntry.setExecutionTime(DateHelper.diff(Calendar.getInstance(), (Calendar) msgContext.getProperty("start-time")));

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Проблемы с записью в журнал сообщений", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	protected String getRequestMessageType(SOAPBody body) throws LoggingException
	{
		return "base";
	}

	protected String getMessageId(SOAPBody body) throws LoggingException
	{
		return AxisLoggerHelper.resolveMessageId(body);
	}

	protected String getResponseMessageType(SOAPBody body) throws LoggingException
	{
		return "base";
	}

	protected String getMessageRequest(Message request) throws LoggingException
	{
		return AxisLoggerHelper.serializeMessage(request);
	}

	protected String getMessageResponse(Message response) throws LoggingException
	{
		return AxisLoggerHelper.serializeMessage(response);
	}

	/**
	 * Определяет использование логирования сообщений
	 * @return Флаг использования логирования (default true)
	 */
	protected boolean use()
	{
		return true;
	}

	public void onFault(MessageContext msgContext)
	{
		super.onFault(msgContext);
		writeToLog(msgContext);
	}
}
