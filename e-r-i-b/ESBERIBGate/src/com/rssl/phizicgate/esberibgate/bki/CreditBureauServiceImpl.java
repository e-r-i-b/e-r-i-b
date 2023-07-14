package com.rssl.phizicgate.esberibgate.bki;

import com.rssl.phizgate.common.payments.offline.OutgoingRequestEntry;
import com.rssl.phizgate.common.payments.offline.OutgoingRequestService;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.EtsmConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bki.BKIRequestType;
import com.rssl.phizic.gate.bki.CreditBureauService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;

import java.io.StringWriter;
import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import static com.rssl.phizic.logging.messaging.System.creditprx;

/**
 * Сервис БКИ
 * @author Puzikov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public class CreditBureauServiceImpl extends AbstractService implements CreditBureauService
{
	private static final String REQUEST_MESSAGE_TYPE = "enquiryRequestERIB";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final EnquiryRequestEribBuilder requestBuilder = new EnquiryRequestEribBuilder();
	private final JmsService jmsService = new JmsService();
	private final JAXBContext jaxbContext;

	private final OutgoingRequestService outgoingRequestService = new OutgoingRequestService();

	/**
	 * ctor
	 * @param factory фабрика шлюзов
	 */
	public CreditBureauServiceImpl(GateFactory factory)
	{
		super(factory);
		try
		{
			jaxbContext = JAXBContext.newInstance(EnquiryRequestERIB.class);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[BKI] Сбой на загрузке JAXB-контекста", e);
		}
	}

	public void sendCheckCreditHistory(Client client) throws GateException
	{
		EnquiryRequestERIB request = requestBuilder.makeCheckRequest(client);

		send(request);
		saveOutgoingRequest(request, BKIRequestType.BKICheckCreditHistory, client, null);
	}

	public void sendGetCreditHistory(Client client, GateDocument payment) throws GateException
	{
		EnquiryRequestERIB request = requestBuilder.makeGetRequest(client, payment);

		send(request);
		saveOutgoingRequest(request, BKIRequestType.BKIGetCreditHistory, client, payment.getId());
	}

	private void send(EnquiryRequestERIB request) throws GateException
	{
		EtsmConfig etsmConfig = ConfigFactory.getConfig(EtsmConfig.class);
		String queueName = etsmConfig.getEsbCreditQueueName();
		String qcfName = etsmConfig.getEsbCreditQCFName();

		String requestXml = null;
		String requestId = null;
		try
		{
			requestXml = makeXml(request);
			requestId = request.getRqUID();
			jmsService.sendMessageToQueue(requestXml, queueName, qcfName, null, null);
		}
		catch (JAXBException e)
		{
			throw new GateException("Сбой на упаковке заявки в XML", e);
		}
		catch (JMSException e)
		{
			throw new GateException("Сбой на отправке заявки в очередь", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("Сбой на отправке заявки", e);
		}
		finally
		{
			// Логирование заявки в Журнал сообщений
			writeMessageLog(requestXml, requestId);
		}
	}

	private void saveOutgoingRequest(EnquiryRequestERIB request, BKIRequestType requestType, Client client, Long paymentId) throws GateException
	{
		try
		{
			Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();

			OutgoingRequestEntry requestEntry = new OutgoingRequestEntry();
			requestEntry.rqUID = request.getRqUID();
			requestEntry.rqTime = request.getRqTm();
			requestEntry.rqType = requestType.name();
			requestEntry.operUID = request.getOperUID();
			requestEntry.nodeNumber = nodeNumber;
			requestEntry.loginId = client.getInternalId();
			requestEntry.paymentId = paymentId;

			outgoingRequestService.addRequestEntry(requestEntry);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private String makeXml(EnquiryRequestERIB request) throws JAXBException
	{
		StringWriter writer = new StringWriter();
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.marshal(request, writer);
		return writer.toString();
	}

	private void writeMessageLog(String requestXMLString, String requestID)
	{
		if (requestXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(creditprx);
			logEntry.setMessageRequestId(requestID);
			logEntry.setMessageType(REQUEST_MESSAGE_TYPE);
			logEntry.setMessageRequest(requestXMLString);
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Проблемы с записью в журнал сообщений", e);
		}
	}
}
