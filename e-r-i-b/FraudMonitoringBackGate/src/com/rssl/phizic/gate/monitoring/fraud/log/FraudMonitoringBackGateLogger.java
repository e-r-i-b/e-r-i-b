package com.rssl.phizic.gate.monitoring.fraud.log;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.monitoring.fraud.ConfigImpl;
import com.rssl.phizic.gate.monitoring.fraud.utils.ClientTransactionCompositeId;
import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import org.apache.axis.MessageContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

/**
 * Логер сообщений приложений FraudMonitoringBackGateApp - ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringBackGateLogger extends AbstractAxisLogger
{
	public FraudMonitoringBackGateLogger()
	{
		super(System.FMBackGate);
	}

	protected boolean use()
	{
		ConfigImpl config = ConfigFactory.getConfig(ConfigImpl.class);
		return config.isNeedMessageLogging();
	}

	protected void writeToLog(MessageContext context)
	{
		try
		{
			String request  = AxisLoggerHelper.serializeMessage(context.getRequestMessage());
			String response = AxisLoggerHelper.serializeMessage(context.getResponseMessage());

			initialize(request);

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(createEntry(request, response, (Calendar) context.getProperty("start-time")));
		}
		catch (Exception e)
		{
			log.error("Проблемы с записью в журнал сообщений", e);
		}
	}

	private void initialize(String request) throws LoggingException
	{
		MessageHandler handler = new MessageHandler();
		AxisLoggerHelper.parse(request, handler);

		LogThreadContext.setLoginId(handler.getNodeLoginId());
		LogThreadContext.setNodeId(handler.getNodeId());
	}

	private MessagingLogEntry createEntry(String request, String response, Calendar startDate) throws LoggingException
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		logEntry.setSystem(getSystemId());
		logEntry.setMessageType("base");
		logEntry.setMessageRequestId(AxisLoggerHelper.UNKNOWN_ID);
		logEntry.setMessageRequest(request);
		logEntry.setMessageResponseId(AxisLoggerHelper.UNKNOWN_ID);
		logEntry.setMessageResponse(response);
		logEntry.setExecutionTime(DateHelper.diff(Calendar.getInstance(), startDate));
		return logEntry;
	}

	private class MessageHandler extends DefaultHandler
	{
		private static final String CLIENT_TRANSACTION_ID_PARAMETER_NAME                    = "clientTransactionId";

		private Long nodeId;
		private Long nodeLoginId;
		private String tagText;

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			tagText = "";
			if (qName.equals(CLIENT_TRANSACTION_ID_PARAMETER_NAME))
			{
				nodeId = null;
				nodeLoginId = null;
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equals(CLIENT_TRANSACTION_ID_PARAMETER_NAME))
			{
				ClientTransactionCompositeId compositeId = new ClientTransactionCompositeId(tagText);
				nodeId = compositeId.getNodeId();
				nodeLoginId = compositeId.getNodeLoginId();
			}
		}

		public void characters(char ch[], int start, int length) throws SAXException
		{
			tagText += new String(ch, start, length);
		}

		public Long getNodeId()
		{
			return nodeId;
		}

		public Long getNodeLoginId()
		{
			return nodeLoginId;
		}
	}
}
