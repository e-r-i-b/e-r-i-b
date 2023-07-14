package com.rssl.phizicgate.esberibgate.credit;

import com.rssl.ikfl.crediting.OfferResponseChannel;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.MarketingResponseType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RegisterRespondToMarketingProposeRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SPNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.ESBMessageCreator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.JMSTransportProvider;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Calendar;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Сендер для отправки информации об отказах изменения кредитного лимита
 * @author Pankin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CRMLimitRefusesSender
{
	private static final String MESSAGE_TYPE = "RegisterRespondToMarketingProposeRq";
	private static final String SYSTEM_ID = "urn:sbrfsystems:99-crm";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final JAXBContext jaxbContext;

	/**
	 * ctor
	 */
	public CRMLimitRefusesSender()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(RegisterRespondToMarketingProposeRq.class);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ESB] Сбой на загрузке JAXB-контекста", e);
		}
	}

	/**
	 * Отправка отказа предложения на изменение кредитного лимита
	 * @param phone телефон
	 * @param eventTime время получения отказа
	 * @return Сообщение jms
	 */
	public final Message send(String phone, Calendar eventTime) throws GateException, GateLogicException
	{
		String requestXMLString = null;
		String requestID = null;

		try
		{

			RegisterRespondToMarketingProposeRq request = makeRequest(phone, eventTime);
			requestID = request.getRqUID();

			requestXMLString = formatRequestXML(request);
			Message message = JMSTransportProvider.getInstance(ESBSegment.federal).doRequest(requestXMLString, ESBMessageCreator.getInstance());
			return message;
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
		finally
		{
			// Логирование заявки в Журнал сообщений
			writeMessageLog(requestXMLString, requestID, MESSAGE_TYPE);
		}
	}

	protected RegisterRespondToMarketingProposeRq makeRequest(String phone, Calendar eventTime)
	{

		RegisterRespondToMarketingProposeRq request = new RegisterRespondToMarketingProposeRq();

		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		request.setSPName(SPNameType.BP_ERIB);
		request.setSystemId(SYSTEM_ID);

		MarketingResponseType marketingResponse = new MarketingResponseType();
		marketingResponse.setMethod(OfferResponseChannel.MB.crmCode);
		marketingResponse.setResult("03");
		marketingResponse.setDetailResult("03");
		marketingResponse.setPhoneNumber(new BigInteger(phone));
		marketingResponse.setResponseDateTime(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(eventTime));

		request.getMarketingResponses().add(marketingResponse);

		return request;
	}

	private String formatRequestXML(RegisterRespondToMarketingProposeRq request) throws JAXBException
	{
		StringWriter writer = new StringWriter();
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.marshal(request, writer);
		return writer.toString();
	}

	private void writeMessageLog(String requestXMLString, String requestID, String messageType)
	{
		if (requestXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(com.rssl.phizic.logging.messaging.System.esberib);
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
