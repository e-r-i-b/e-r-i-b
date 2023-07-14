package com.rssl.phizicgate.esberibgate.loanclaim.etsm;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.EtsmConfig;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.config.loanclaim.XSDReleaseVersion;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loanclaim.type.ChannelType;
import com.rssl.phizic.gate.payments.loan.ETSMLoanClaim;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;

import java.io.StringWriter;
import java.util.Calendar;
import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Erkin
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сендер для расширенной заявки на кредит.
 * Отправляет заявку в ETSM (текстовое JMS в XML-формате).
 */
public class ETSMLoanClaimSender extends AbstractDocumentSenderBase
{
	private static final String REQUEST_MESSAGE_TYPE = "ChargeLoanApplicationRq";
	private static final String RELEASE_19_MARK = "r19";

	private final JmsService jmsService = new JmsService();

	private final JAXBContext jaxbContextRelease13;
	private final JAXBContext jaxbContextRelease16;
	private final JAXBContext jaxbContextRelease19;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param factory - фабрика шлюзов
	 */
	public ETSMLoanClaimSender(GateFactory factory)
	{
		super(factory);
		try
		{
			jaxbContextRelease13 = JAXBContext.newInstance(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq.class);
			jaxbContextRelease16 = JAXBContext.newInstance(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq.class);
			jaxbContextRelease19 = JAXBContext.newInstance(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ChargeLoanApplicationRq.class);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ETSM] Сбой на загрузке JAXB-контекста", e);
		}
	}

	public void prepare(GateDocument document)
	{
		// делать нечего
	}

	public void confirm(GateDocument document)
	{
		// делать нечего
	}

	public void validate(GateDocument document)
	{
		// делать нечего
	}

	public void send(GateDocument document) throws GateException
	{
		ETSMLoanClaim loanClaim = (ETSMLoanClaim) document;

		if (loanClaim.getChannel().equals(ChannelType.GUEST) && !loanClaim.getOwnerGuestMbk())
			return;


		EtsmConfig etsmConfig = ConfigFactory.getConfig(EtsmConfig.class);
		String queueName = etsmConfig.getEsbCreditQueueName();
		String qcfName = etsmConfig.getEsbCreditQCFName();

		String requestID = null;
		String requestXMLString = null;
		boolean isError = false;
		try
		{
			// 0. Очищаем поля ETSM в заявке
			loanClaim.resetClaimStatus();

			// 1. Генерируем OperUID
			String operUID = new RandomGUID().getStringValue();
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			XSDReleaseVersion xsdReleaseVersion = loanClaimConfig.getXSDReleaseVersion();
			switch (xsdReleaseVersion)
			{
				case VERSION_19:
				{
					ETSMLoanClaimRequestBuilderRelease19 requestBuilder = new ETSMLoanClaimRequestBuilderRelease19();
					String claimOperUID = loanClaim.getOperationUID();
					String nodeNumber = StringHelper.addLeadingZeros(RELEASE_19_MARK + claimOperUID.substring(claimOperUID.length()-2), 7);
					operUID = claimOperUID.substring(0, claimOperUID.length()-2) + nodeNumber;
					com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ChargeLoanApplicationRq request = requestBuilder.makeRequest(loanClaim, operUID);

					requestID = request.getRqUID();
					requestXMLString = formatRequestXML(request, xsdReleaseVersion);

					break;
				}
				case VERSION_16:
				{

					// 2. Конвертируем заявку в запрос к ETSM
					ETSMLoanClaimRequestBuilderRelease16 requestBuilder = new ETSMLoanClaimRequestBuilderRelease16();
					com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq request = requestBuilder.makeRequest(loanClaim, operUID);
					// 3. Конвертируем ETSM-запрос в XML
					requestID = request.getRqUID();
					requestXMLString = formatRequestXML(request, xsdReleaseVersion);

					break;
				}
				default:
				{
					// 2. Конвертируем заявку в запрос к ETSM
					ETSMLoanClaimRequestBuilderRelease13 requestBuilder = new ETSMLoanClaimRequestBuilderRelease13();
					com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq request = requestBuilder.makeRequest(loanClaim, operUID);
					// 3. Конвертируем ETSM-запрос в XML
					requestID = request.getRqUID();
					requestXMLString = formatRequestXML(request, xsdReleaseVersion);
				}
			}

			// 4. Отправляем XML-запрос в ETSM
			jmsService.sendMessageToQueue(requestXMLString, queueName, qcfName, null, null);

			// 5. Сохраняем OperUID в заявке
			loanClaim.setExternalId(operUID);

			// 6. Запоминаем заявку в базе оффлайн-документов (в базе обратного потока), в 19 релизе в базе оффлайн документов не сохраняем, номер блока в operUID
			if (!xsdReleaseVersion.equals(XSDReleaseVersion.VERSION_19))
				addOfflineDocumentInfo(document);
		}
		catch (JAXBException e)
		{
			isError = true;
			throw new GateException("Сбой на упаковке заявки в XML", e);
		}
		catch (JMSException e)
		{
			isError = true;
			throw new GateException("Сбой на отправке заявки в очередь Transact SM", e);
		}
		catch (RuntimeException e)
		{
			isError = true;
			throw new GateException("Сбой на отправке заявки", e);
		}
		finally
		{
			// Логирование заявки для мониторинга бизнес операций.
			writeMonitoringLog(loanClaim, isError);
			// Логирование заявки в Журнал сообщений
			writeMessageLog(requestXMLString, requestID);
		}
	}

	private void writeMonitoringLog(ETSMLoanClaim loanClaim, boolean isError)
	{
		MonitoringOperationConfig config = ConfigFactory.getConfig(MonitoringOperationConfig.class);
		if (!config.isActive())
			return;

		try
		{
			MonitoringLogEntry entry = new MonitoringLogEntry();
			entry.setApplication(loanClaim.getClientCreationChannel().name());
			entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			entry.setCreationDate(loanClaim.getClientCreationDate());
			entry.setStartDate(Calendar.getInstance());
			entry.setStateCode(isError ? "ERROR" : "DISPATCHED");
			entry.setDocumentType(MonitoringDocumentType.CREDIT.name());
			entry.setTb(loanClaim.getTb());
			config.writeLog(entry);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Gate).error(e);
		}
	}

	private String formatRequestXML(Object request, XSDReleaseVersion xsdReleaseVersion) throws JAXBException
	{
		StringWriter writer = new StringWriter();
		Marshaller marshaller;

		switch (xsdReleaseVersion)
		{
			case VERSION_19:
			{
				marshaller = jaxbContextRelease19.createMarshaller();
				break;
			}
			case VERSION_16:
			{
				marshaller = jaxbContextRelease16.createMarshaller();
				break;
			}
			default:
			{
				marshaller = jaxbContextRelease13.createMarshaller();
			}
		}

		marshaller.marshal(request, writer);
		return writer.toString();
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		send(document);
	}

	public void rollback(WithdrawDocument document)
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	private void writeMessageLog(String requestXMLString, String requestID)
	{
		if (requestXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(System.creditprx);
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
