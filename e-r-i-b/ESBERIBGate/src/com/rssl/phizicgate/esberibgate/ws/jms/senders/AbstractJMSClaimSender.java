package com.rssl.phizicgate.esberibgate.ws.jms.senders;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.ClientInfoDocument;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.JMSTransportProvider;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.RequestBuilder;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author bogdanov
 * @ created 06.05.2014
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractJMSClaimSender<D, R> extends AbstractService
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final IssueCardService issueCardService = new IssueCardService();
	private final JAXBContext jaxbContext;

	protected AbstractJMSClaimSender(GateFactory factory, Class<R> requestType)
	{
		super(factory);
		try
		{
			jaxbContext = JAXBContext.newInstance(requestType);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ESB] Сбой на загрузке JAXB-контекста", e);
		}
	}

	protected abstract RequestBuilder<R, D> getRequestBuilder(GateFactory factory);

	protected abstract void setUUID(D document, String uuid);

	protected abstract String getUUID(D document);

	/**
	 * Отправляет документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public final void send(final D document) throws GateException, GateLogicException
	{
		String requestXMLString = null;
		String requestID = null;
		String messageType = null;

		try
		{
			RequestBuilder<R, D> requestBuilder = getRequestBuilder(getFactory());
			R request = requestBuilder.makeRequest(document);
			requestID = requestBuilder.getRequestId(request);
			messageType = requestBuilder.getRequestMessageType();

			requestXMLString = formatRequestXML(request);
			setUUID(document, requestID);
			if (document instanceof GateDocument)
				issueCardService.addOrUpdate((GateDocument) document);
			else if (document instanceof ClientInfoDocument)
				issueCardService.addOrUpdate((IssueCardDocumentImpl) document);
			else
				issueCardService.addOrUpdate((CardInfoImpl) document);
			JMSTransportProvider.getInstance(ESBSegment.federal).doRequest(requestXMLString, ESBMessageCreator.getInstance());
		}
		catch (JAXBException e)
		{
			throw new GateException("Сбой на упаковке заявки в XML. ESB", e);
		}
		catch (JMSException e)
		{
			throw new GateException("Сбой на отправке заявки в очередь. ESB", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("Сбой на отправке заявки. ESB", e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new GateException("Сбой на отправке заявки. ESB", e);
		}
		finally
		{
			// Логирование заявки в Журнал сообщений
			writeMessageLog(requestXMLString, requestID, messageType);
		}

	}

	private String formatRequestXML(R request) throws JAXBException, UnsupportedEncodingException
	{
		StringWriter writer = new StringWriter();
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.marshal(request, writer);
		return new String(writer.toString().getBytes("UTF-8"), "UTF-8");
	}

	private void writeMessageLog(String requestXMLString, String requestID, String messageType)
	{
		if (requestXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(System.esberib);
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
