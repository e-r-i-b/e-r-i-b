package com.rssl.phizgate.messaging.internalws.server;

import com.rssl.phizgate.messaging.internalws.Constants;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * ’елпер дл€ логировани€ сообщений
 * @author Pankin
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class Logger
{
	private static final List<String> MASK_FIELDS = Arrays.asList(Constants.PASSWORD_TAG,
			Constants.CONFIRMATION_CODE_TAG, Constants.OUID_TAG,
			Constants.GUID_TAG);
	private static final String MASKED_VALUE = "***";

	private String messageId;
	private System system;
	private String messageType;
	private String application;
	private String errorCode;
	private InnerSerializer serializer;

	/**
	 * «аписать в лог
	 * @param request запрос
	 * @param response ответ
	 * @param duration врем€ выполнени€
	 */
	public void writeToLog(String request, String response, Long duration) throws Exception
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		StringWriter requestWriter = new StringWriter();
		serializer = new InnerSerializer(requestWriter, "utf-8");
		SaxFilter parser = new SaxFilter(XmlHelper.newXMLReader());
		parser.parse(new InputSource(new StringReader(request)));
		logEntry.setMessageRequestId(messageId);
		logEntry.setMessageType(messageType);
		logEntry.setSystem(system);
		logEntry.setMessageRequest(requestWriter.toString());

		if (!StringHelper.isEmpty(response))
		{
			StringWriter responseWriter = new StringWriter();
			serializer = new InnerSerializer(responseWriter, "utf-8");
			parser.parse(new InputSource(new StringReader(response)));
			logEntry.setMessageResponseId(messageId);
			logEntry.setMessageResponse(responseWriter.toString());
		}

		logEntry.setExecutionTime(duration);
		logEntry.setErrorCode(errorCode);
		//если задано в запросе берем оттуда, если нет используем из контекста
		if(!StringHelper.isEmpty(application))
			logEntry.setApplication(Application.valueOf(application));

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		writer.write(logEntry);
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private String tagText = "";

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startDocument() throws SAXException
		{
			serializer.startDocument();
		}

		public void endDocument() throws SAXException
		{
			serializer.endDocument();
		}

		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
		{
			tagText = "";
			serializer.startElement(uri, localName, qName, atts);
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			tagText += new String(ch, start, length);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			// ћаскируем пол€, содержащие пароли и mGUID
			if (MASK_FIELDS.contains(qName))
				serializer.characters(MASKED_VALUE.toCharArray(), 0, MASKED_VALUE.length());
			else
				serializer.characters(tagText.toCharArray(), 0, tagText.length());
			serializer.endElement(uri, localName, qName);

			if (qName.equalsIgnoreCase(Constants.UID_TAG))
				messageId = tagText;

			if (!qName.equalsIgnoreCase("message"))
				messageType = qName;
			if(qName.equalsIgnoreCase("source"))
				application = tagText;
			if(qName.equalsIgnoreCase("system"))
				system = System.fromValue(tagText);
			if(qName.equalsIgnoreCase("code"))
		   	    errorCode = tagText;
			tagText = "";
		}
	}
}
