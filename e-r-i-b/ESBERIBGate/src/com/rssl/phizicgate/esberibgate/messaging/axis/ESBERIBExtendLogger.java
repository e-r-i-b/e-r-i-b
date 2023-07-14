package com.rssl.phizicgate.esberibgate.messaging.axis;

import com.rssl.phizic.logging.AbstractAxisLogger;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.AllNamesFoundException;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 02.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ESBERIBExtendLogger extends AbstractAxisLogger
{

	protected class MessageHandler extends DefaultHandler
	{
		protected MessagingLogEntry logEntry;
		private int level = 0;            //уровень вложени€ текущего тега
		private boolean filledMessageType = false;
		private boolean filledMessageId = false;
		protected StringBuilder currentElementValue;
		protected final boolean request;
		protected int messageTypeLevel;

		/**
		 *  онструктор хэндлера
		 * @param logEntry запись лога
		 * @param request €вл€етс€ ли сообщение запросом
		 */
		protected MessageHandler(MessagingLogEntry logEntry, boolean request)
		{
			this.logEntry = logEntry;
			this.request = request;
			messageTypeLevel = 2; //тег -тип сообщени€ обычно находитс€ на втором уровне вложени€
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			checkComplete();
			if (++level == messageTypeLevel)
				setMessageType(qName);
			if (qName.equals("RqUID"))
				currentElementValue = new StringBuilder();
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			if (currentElementValue != null)
				currentElementValue.append(ch, start, length);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equals("RqUID")) // тег, содержащий id сообщени€, - обычно RqUID
			{
				setMessageId(currentElementValue.toString());
				filledMessageId = true;
			}
			checkComplete();
			currentElementValue = null;
			--level;
		}

		/**
		 * ¬ообще этот метод не должен вызыватьс€ при нормальной обработке -
		 * по сценарию, мы должны выйти раньше, за исключением особых случаев.
		 * Ќо если что-то пошло не так,
		 * нам нужно заполнить logEntry дефолными значени€ми.
		 * @see #checkComplete
		 * @throws SAXException
		 */
		public void endDocument() throws SAXException
		{
			if (!filledMessageType)
				setMessageType("unknown");
			if (!filledMessageId)
				setMessageId("0");
		}

		protected void setMessageId(String id)
		{
			if (request)
				logEntry.setMessageRequestId(id);
			else
				logEntry.setMessageResponseId(id);
			filledMessageId = true;
		}

		protected void setMessageType(String type)
		{
			if (filledMessageType)
				return;
			if (request)
				logEntry.setMessageType(type);

			filledMessageType = true;
		}

		protected void checkComplete() throws SAXException
		{
			if (filledMessageId && filledMessageType)
				throw new AllNamesFoundException();
		}
	}

	public ESBERIBExtendLogger()
	{
		super(com.rssl.phizic.logging.messaging.System.esberib);
	}


	protected void writeToLog(MessageContext msgContext)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			Message request = msgContext.getRequestMessage();
			Message response = msgContext.getResponseMessage();
			String requestBody = getContent(request);
			String responseBody = getContent(response);

			logEntry.setSystem(getSystemId());

			logEntry.setMessageRequest(AxisLoggerHelper.serializeMessage(request));
			logEntry.setMessageResponse(AxisLoggerHelper.serializeMessage(response));

			AxisLoggerHelper.parse(requestBody, getMessageHandler(logEntry, true));
			AxisLoggerHelper.parse(responseBody, getMessageHandler(logEntry, false));

			logEntry.setExecutionTime(DateHelper.diff(Calendar.getInstance(), (Calendar) msgContext.getProperty("start-time")));

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("ѕроблемы с записью в журнал сообщений", e);
		}
	}

	/**
	 * в сообщени€х, обрабатываемых этим логгером,
	 * информативна€ часть сообщени€ хранитс€ в экранированном XML
	 * внутри тега req или resp. ѕоэтому вытаскиваетс€ она по-другому.
	 * @param message сообщение
	 * @return тело сообщени€
	 * @throws GateException
	 */
	protected String getContent(Message message) throws GateException
	{
		try
		{
			return message.getSOAPBody().//<soapenv:body>
					getFirstChild().//<DoIFX>
					getFirstChild().//<req/resp>
					getFirstChild().//сам текст
					getNodeValue();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	protected DefaultHandler getMessageHandler(MessagingLogEntry logEntry, boolean request)
	{
		return new MessageHandler(logEntry, request);
	}
}
