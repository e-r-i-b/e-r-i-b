package com.rssl.phizicgate.bars.payments;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.impl.DOMMessageImpl;
import com.rssl.phizic.gate.payments.ReceiverNameService;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.bars.messaging.BarsMessageData;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class ReceiverNameServiceImpl implements ReceiverNameService
{
	private GateFactory factory;

	public ReceiverNameServiceImpl(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * Заполнить ответ из БАРСа с исключением
	 * @param excName код исключения
	 * @param excMessage текст исключения
	 * @return ответ с исключением
	 * @throws GateException
	 */
	protected BarsMessageData getExceptionResponce(String excName, String excMessage) throws GateException
	{
		try
		{
			GateMessage message = new DOMMessageImpl("XsbRemoteClientNameReturn");
			Element exceptionItems = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "exceptionItems");
			Element exceptionItem = XmlHelper.appendSimpleElement(exceptionItems, "XsbExceptionItem");
			XmlHelper.appendSimpleElement(exceptionItem, "excMessage", excMessage);
			XmlHelper.appendSimpleElement(exceptionItem, "excName", excName);

			BarsMessageData messageData = new BarsMessageData();
			messageData.setBody(XmlHelper.convertDomToText(message.getDocument()));

			return messageData;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Создать сообщение по ответу БАРСа и заполнить данные
	 * @param sSName имя получателя из ответа БАРСа
	 * @param sInn ИНН получателя из ответа БАРСа
	 * @return данный сообщения БАРС
	 * @throws GateException
	 */
	protected BarsMessageData getResponse(String sSName, String sInn) throws GateException
	{
		try
		{
			//Создаем сообщение
			GateMessage message = new DOMMessageImpl("XsbRemoteClientNameReturn");
			message.addParameter("SSName", sSName);
			message.addParameter("SInn", sInn);
			//Устанавливаем messageData
			BarsMessageData messageData = new BarsMessageData();
			messageData.setBody(XmlHelper.convertDomToText(message.getDocument()));

			return messageData;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Логирование сообщений с БАРСом
	 * xml-сообщения барса исполльзуется кодировка windows-1251
	 * @param request запрос
	 * @param response ответ
	 * @throws GateException
	 */
	protected void writeToLog(BarsMessageData request, BarsMessageData response, Long executionTime) throws GateException
	{
		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		logEntry.setMessageType("XSB");
		logEntry.setMessageRequestId(new RandomGUID().getStringValue());
		logEntry.setMessageRequest(request.getBodyAsString("windows-1251"));
		logEntry.setSystem(com.rssl.phizic.logging.messaging.System.fromValue("bars"));
		logEntry.setMessageResponse(response.getBodyAsString("windows-1251"));
		logEntry.setExecutionTime(executionTime);

		try
		{
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public GateFactory getFactory()
	{
		return factory;
	}
}
