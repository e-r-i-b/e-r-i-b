package com.rssl.phizic.mdm.client.service.log;

import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.*;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.message.SOAPEnvelope;
import org.w3c.dom.Node;

import java.io.StringWriter;
import javax.xml.soap.SOAPBody;

/**
 * @author akrenev
 * @ created 09.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ’елпер логировани€ сообщений в MDMApp
 */

public class MDMClientLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String UNKNOWN_TYPE = "unknown";
	private static final String UNKNOWN_ENVELOPE = "unknown SOAP-envelope of message (or message is not from Axis)";
	private static final String NULL_MESSAGE = "null message";

	/**
	 * записать сообщение в лог
	 * @param messageContext контекст сообщени€
	 * @param duration врем€ выполнени€
	 */
	public static void writeToLog(MessageContext messageContext, Long duration)
	{
		try
		{
			Message request = messageContext.getRequestMessage();
			Message response = messageContext.getResponseMessage();

			MessagingLogEntry logEntry = getLogEntry(duration, request, response);

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception ex)
		{
			log.error("ѕроблемы с записью в журнал сообщений", ex);
		}
	}

	private static MessagingLogEntry getLogEntry(Long duration, Message request, Message response) throws Exception
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		SOAPBody requestBody = AxisLoggerHelper.getBody(request);

		logEntry.setSystem(System.MDMApp);

		logEntry.setMessageRequestId(AxisLoggerHelper.resolveMessageId(requestBody));
		logEntry.setMessageType(getMessageType(requestBody));
		logEntry.setMessageRequest(serializeMessage(request));

		try
		{
			SOAPBody responseBody = AxisLoggerHelper.getBody(response);
			logEntry.setMessageResponseId(AxisLoggerHelper.resolveMessageId(responseBody));
			logEntry.setMessageResponse(serializeMessage(response));
		}
		catch (Exception ex)
		{
			log.error("ѕроблемы с записью ответа в журнал сообщений", ex);
		}

		logEntry.setExecutionTime(duration);
		return logEntry;
	}

	private static String getMessageType(SOAPBody body) throws Exception
	{
		if (body == null)
			return UNKNOWN_TYPE;

		// request
		Node node = body.getFirstChild();
		if (node != null)
			// Data
			node = node.getLastChild();
		if (node != null)
			// основной тег
			node = node.getFirstChild();
		if (node != null)
			return node.getNodeName();

		return UNKNOWN_TYPE;
	}

	private static String serializeMessage(Message message) throws Exception
	{
		if (message == null)
			return NULL_MESSAGE;

		MessageContext msgContext = message.getMessageContext();

		SOAPEnvelope envelope = message.getSOAPEnvelope();

		if (envelope == null)
			return UNKNOWN_ENVELOPE;

		StringWriter writer = new StringWriter();
		SerializationContext context = new SerializationContext(writer, msgContext);
		envelope.output(context);
		writer.close();
		return writer.getBuffer().toString();
	}
}
