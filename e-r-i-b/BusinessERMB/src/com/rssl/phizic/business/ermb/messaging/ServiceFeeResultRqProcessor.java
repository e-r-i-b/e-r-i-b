package com.rssl.phizic.business.ermb.messaging;

import com.rssl.phizic.business.ermb.writedown.WriteDownBusinessProcessor;
import com.rssl.phizic.business.ermb.writedown.WriteDownResult;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.messaging.ermb.TransportMessageSerializer;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.types.PaymentPeriodType;
import com.rssl.phizic.synchronization.types.ServiceFeeResultRq;
import com.rssl.phizic.synchronization.types.ServiceStatusRes;
import com.rssl.phizic.synchronization.types.ServiceType;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.jms.JmsService;

import java.util.Calendar;
import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.logging.messaging.System.ERMB_SOS;
import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.SERVICE_FEE_RESULT_RS_CQF;
import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.SERVICE_FEE_RESULT_RS_QUEUE;

/**
 * Обработчик сообщения из СОС результата списания абонентской платы
 * @author Puzikov
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ServiceFeeResultRqProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	private static final JmsService jmsService = new JmsService();
	private static final TransportMessageSerializer messageSerializer = new TransportMessageSerializer();

	/**
	 * ctor
	 * @param module - модуль
	 */
	protected ServiceFeeResultRqProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		ServiceFeeResultRq serviceFeeResultRequest = xmlRequest.getServiceFeeResultRq();
		try
		{
			WriteDownBusinessProcessor processor = new WriteDownBusinessProcessor();
			WriteDownResult writeDownResult = processor.process(serviceFeeResultRequest);

			ServiceStatusRes response = buildResponse(serviceFeeResultRequest, writeDownResult);
			sendResponse(response);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			UUID rqUID = (xmlRequest != null) ? serviceFeeResultRequest.getRqUID() : null;
			writeMessageLog(rqUID, xmlRequest.getMessage(), ServiceFeeResultRq.class.getSimpleName());
		}
	}

	private ServiceStatusRes buildResponse(ServiceFeeResultRq request, WriteDownResult result)
	{
		ServiceStatusRes response = new ServiceStatusRes();
		response.setRqVersion(new VersionNumber(1, 0));
		response.setRqUID(new RandomGUID().toUUID());
		response.setRqTime(Calendar.getInstance());
		response.setCorrelationID(request.getRqUID());

		response.setPhone(result.getPhoneNumber());
		if (result.isSuccessful() && result.getWriteOffPeriod() != null)
		{
			PaymentPeriodType paymentPeriodType = new PaymentPeriodType();
			paymentPeriodType.setFirstDate(result.getWriteOffPeriod().getFromDate());
			paymentPeriodType.setLastDate(result.getWriteOffPeriod().getToDate());
			response.setPaymentPeriod(paymentPeriodType);
		}

		ServiceType serviceType = new ServiceType();
		serviceType.setOldStatus(result.getOldServiceStatus());
		serviceType.setCurrentStatus(result.getNewServiceStatus());
		response.setService(serviceType);

		return response;
	}

	private void sendResponse(ServiceStatusRes response) throws JAXBException, JMSException
	{
		String responseXml = messageSerializer.marshallServiceFeeResultResponse(response);
		jmsService.sendMessageToQueue(responseXml, SERVICE_FEE_RESULT_RS_QUEUE, SERVICE_FEE_RESULT_RS_CQF, null, null);
		writeMessageLog(response.getRqUID(), responseXml, ServiceStatusRes.class.getSimpleName());
	}

	private void writeMessageLog(UUID uuid, String messageXML, String messageType)
	{
		try
		{
			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageRequestId((uuid != null) ? uuid.toString() : "unexpected-id");
			logEntry.setMessageRequest(messageXML);
			logEntry.setMessageType(messageType);
			logEntry.setApplication(Application.ErmbTransportChannel);
			logEntry.setSystem(ERMB_SOS);

			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}

	@Override
	public boolean writeToLog()
	{
		// собственное логирование
		return false;
	}
}
