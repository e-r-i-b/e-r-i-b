package com.rssl.phizic.credit;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizgate.common.credit.bki.BKIConstants;
import com.rssl.phizgate.common.credit.bki.BKIResponse;
import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizgate.common.payments.offline.OutgoingRequestEntry;
import com.rssl.phizgate.common.payments.offline.OutgoingRequestService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.etsm.offer.service.OfferPriorWebService;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.bki.BKIRequestType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Calendar;
import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static com.rssl.phizic.logging.messaging.System.creditprx;

/**
 * @author Erkin
 * @ created 21.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обработка сообщений из шины с информацией по кредитам (БКИ/TSM)
 * Транспорт: текстовое JMS-сообщение (Transact SM -> СБОЛ)
 * Формат: XML по схеме LoanApplicationRelease13.xsd или LoanApplicationRelease16.xsd (корневой тег StatusLoanApplicationRq) или ERIB.BKI.Schema.xsd
 */
public class PhizProxyCreditProcessor extends MessageProcessorBase<PhizProxyCreditEjbMessage>
{
	private final OutgoingRequestService outgoingRequestService = new OutgoingRequestService();

	private final JmsService jmsService = new JmsService();

	private final JAXBContext jaxbContext;

	/**
	 * ctor
	 * @param module - модуль
	 */
	public PhizProxyCreditProcessor(Module module)
	{
		super(module);
		try
		{
			jaxbContext = JAXBContext.newInstance(StatusLoanApplicationRq.class);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ETSM] Сбой на загрузке JAXB-контекста", e);
		}
	}

	@Override
	public boolean writeToLog()
	{
		// В классе реализовано собственное логирование
		return false;
	}

	@Override
	@SuppressWarnings("ThrowCaughtLocally")
	protected void doProcessMessage(PhizProxyCreditEjbMessage xmlRequest)
	{
		String operUID = null;
		String requestType = "UNKNOWN";
		String message = xmlRequest.getMessage();

		MonitoringLogEntry logEntry = new MonitoringLogEntry();
		logEntry.setStartDate(Calendar.getInstance());
		logEntry.setCreationDate(Calendar.getInstance());
		logEntry.setDocumentType(MonitoringDocumentType.CRD_DCSN.name());
		logEntry.setApplication(CreationType.system.name());
		logEntry.setNodeId(-1L);
		try
		{
			// 1. Разбираем запрос
			if (xmlRequest.getStatusLoanApplicationRqRelease13() != null)
			{
				logEntry.setStateCode(xmlRequest.getStatusLoanApplicationRqRelease13().getApplicationStatus().getStatus().getStatusCode() == 0 ? "EXECUTED" : "ERROR");
				operUID = processETSMRq(message, xmlRequest.getStatusLoanApplicationRqRelease13().getOperUID());
			}
			else if (xmlRequest.getStatusLoanApplicationRqRelease16() != null)
			{
				logEntry.setStateCode(xmlRequest.getStatusLoanApplicationRqRelease16().getApplicationStatus().getStatus().getStatusCode() == 0 ? "EXECUTED" : "ERROR");
				operUID = processETSMRq(message, xmlRequest.getStatusLoanApplicationRqRelease16().getOperUID());
			}
			else if (xmlRequest.getStatusLoanApplicationRqRelease19() != null)
			{
				logEntry.setStateCode(xmlRequest.getStatusLoanApplicationRqRelease19().getApplicationStatus().getStatus().getStatusCode() == 0 ? "EXECUTED" : "ERROR");
				operUID = processETSMRq(message, xmlRequest.getStatusLoanApplicationRqRelease19().getOperUID());
			}
			else if (xmlRequest.getEnquiryResponseERIB() != null)
			{
				logEntry.setStateCode(xmlRequest.getEnquiryResponseERIB().getErrorCode() == 0L ? "EXECUTED" : "ERROR");
				operUID = processBKIRq(message, xmlRequest.getEnquiryResponseERIB());
			}
			else
			{
				log.error("Неизвестный тип сообщения");
			}

		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			writeRequestToMessageLog(requestType, operUID, message);
			writeMonitoringLog(logEntry);
		}
	}

	private void writeMonitoringLog(MonitoringLogEntry logEntry)
	{
		try
		{
			ConfigFactory.getConfig(MonitoringOperationConfig.class).writeLog(logEntry);
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	private String processETSMRq(String requestXML, String operUID) throws GateException, JMSException
	{
		//С 19 релиза не используем базу оффлайн- докумнетов
		if (StringHelper.isNotEmpty(operUID))
		{
			boolean isRelease19 = "r19".equals(operUID.substring(operUID.length()-5, operUID.length()-2));

			if (!isRelease19)
			{
			// 3. Определяем блок ЕРИБа и кладём заявку в JMS-очередь блока
				OfflineDocumentInfo offlineDocInfo = OfflineDocumentInfoService.getOfflineDocumentInfo(operUID);
				if (offlineDocInfo != null)
					sendToNode(offlineDocInfo.getBlockNumber(), requestXML, ETSMLoanClaimConstants.STATUS_JMS_TYPE);
			}
			else
				sendToNode(Long.valueOf(StringHelper.removeLeadingZeros(operUID.substring(operUID.length()-2))), requestXML, ETSMLoanClaimConstants.STATUS_JMS_TYPE);
			return operUID;
		} else
		{
			//Запрос пришел из ЕКП
			try
			{
				OfferPriorWebService offerPriorWebService = new OfferPriorWebService();
				OfficeLoanClaim officeLoanClaim = new OfficeLoanClaim();
				StatusLoanApplicationRq rq = readRequest(requestXML);
				fillClaimFromRq(officeLoanClaim, rq);

				offerPriorWebService.addOfficeLoanCLaim(officeLoanClaim);
			}
			catch (BusinessException e)
			{
				log.error(e);
			}
		}
		return "";
	}

	private String processBKIRq(String requestXML, EnquiryResponseERIB bkiRequest) throws Exception
	{
		// 2. Находим заявку в базе исходящих запросов
		String rqUID = bkiRequest.getRqUID();
		String operUID = bkiRequest.getOperUID();
		OutgoingRequestEntry requestEntry = outgoingRequestService.getById(rqUID);
		if (requestEntry == null)
			throw new IllegalArgumentException("В базе обратного потока не найден запрос " + operUID);

		// 3. Определяем блок ЕРИБа и кладем заявку в JMS-очередь блока
		BKIRequestType bkiRequestType = BKIRequestType.valueOf(requestEntry.rqType);
		BKIResponse bkiResponse = new BKIResponse(requestXML, bkiRequestType, requestEntry.loginId, requestEntry.paymentId);
		sendToNode(requestEntry.nodeNumber, bkiResponse, BKIConstants.REPORT_JMS_TYPE);
		outgoingRequestService.deleteByRqId(rqUID);
		return operUID;
	}

	private void sendToNode(Long nodeId, Serializable message, String messageType) throws JMSException
	{
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		NodeInfo nodeInfo = nodeInfoConfig.getNode(nodeId);
		if (nodeInfo == null)
			throw new ConfigurationException("Не найден блок " + nodeId);

		MQInfo dictionaryMQ = nodeInfo.getDictionaryMQ();
		jmsService.sendObjectToQueue(message, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), messageType, null);
	}

	private void writeRequestToMessageLog(String requestType, String operId, String requestXML)
	{
		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();

			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageRequestId(operId);
			logEntry.setMessageType(requestType);
			logEntry.setMessageRequest(requestXML);
			logEntry.setApplication(getModule().getApplication());
			logEntry.setSystem(creditprx);

			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи в журнал сообщений", e);
		}
	}

	private StatusLoanApplicationRq readRequest(String requestXML) throws BusinessException
	{
		try
		{
			// Валидацию по XSD-Схеме здесь не проводим, т.к. валидация - задача прокси-листенера
			Reader reader = new StringReader(requestXML);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (StatusLoanApplicationRq) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	private void fillClaimFromRq(OfficeLoanClaim claim, StatusLoanApplicationRq rq)
	{
		claim.setApplicationNumber(rq.getApplicationStatus().getStatus().getApplicationNumber());
		claim.setState(rq.getApplicationStatus().getStatus().getStatusCode());
		claim.setFioKI(rq.getApplicationStatus().getApplication().getFullNameKI());
		claim.setLoginKI(rq.getApplicationStatus().getApplication().getLoginKI());
		claim.setFioTM(rq.getApplicationStatus().getApplication().getFullNameTM());
		claim.setLoginTM(rq.getApplicationStatus().getApplication().getLoginTM());
		claim.setChannel(rq.getApplicationStatus().getApplication().getChannel());
		claim.setAgreementDate(rq.getApplicationStatus().getApplication().getAppSigningDate());
		claim.setDepartment(rq.getApplicationStatus().getApplication().getUnit());
		claim.setNeedVisitOfficeReason(rq.getApplicationStatus().getReasonCode());
		claim.setType(rq.getApplicationStatus().getApplication().getProduct().getType());
		claim.setProductCode(rq.getApplicationStatus().getApplication().getProduct().getCode());
		claim.setSubProductCode(rq.getApplicationStatus().getApplication().getProduct().getSubProductCode());
		claim.setLoanApprovedAmount(rq.getApplicationStatus().getApproval().getAmount());
		claim.setLoanApprovedPeriod(rq.getApplicationStatus().getApproval().getPeriodM());
		claim.setLoanApprovedRate(rq.getApplicationStatus().getApproval().getInterestRate());
		claim.setProductPeriod(rq.getApplicationStatus().getApplication().getProduct().getPeriod());
		claim.setProductAmount(rq.getApplicationStatus().getApplication().getProduct().getAmount());
		claim.setCurrency(rq.getApplicationStatus().getApplication().getProduct().getCurrency());
		claim.setPaymentType(rq.getApplicationStatus().getApplication().getProduct().getPaymentType());
		claim.setFirstName(rq.getApplicationStatus().getApplication().getApplicant().getFirstName());
		claim.setSurName(rq.getApplicationStatus().getApplication().getApplicant().getLastName());
		claim.setPatrName(rq.getApplicationStatus().getApplication().getApplicant().getMiddleName());
		claim.setBirthDay(rq.getApplicationStatus().getApplication().getApplicant().getBirthday());
		claim.setCitizen(rq.getApplicationStatus().getApplication().getApplicant().getCitizenship());
		claim.setDocumentSeries(rq.getApplicationStatus().getApplication().getApplicant().getIdSeries());
		claim.setDocumentNumber(rq.getApplicationStatus().getApplication().getApplicant().getIdNum());
		claim.setPassportIssueDate(rq.getApplicationStatus().getApplication().getApplicant().getIssueDt());
		claim.setPassportIssueByCode(rq.getApplicationStatus().getApplication().getApplicant().getIssueCode());
		claim.setPassportIssueBy(rq.getApplicationStatus().getApplication().getApplicant().getIssuedBy());
		claim.setHasOldPassport(rq.getApplicationStatus().getApplication().getApplicant().isPrevPassportInfoFlag());
		claim.setRegistrationAddresses(rq.getApplicationStatus().getApplication().getApplicant().getAddress());
		claim.setTypeOfIssue(rq.getApplicationStatus().getApplication().getType());
		claim.setAccountNumber(rq.getApplicationStatus().getApplication().getAcctId());
		claim.setCardNumber(rq.getApplicationStatus().getApplication().getCardNum());
		if (claim.isHasOldPassport())
		{
			claim.setOldDocumentSeries(rq.getApplicationStatus().getApplication().getApplicant().getPrevIdSeries());
			claim.setOldDocumentNumber(rq.getApplicationStatus().getApplication().getApplicant().getPrevIdNum());
			claim.setOldPassportIssueDate(rq.getApplicationStatus().getApplication().getApplicant().getPrevIssueDt());
			claim.setOldPassportIssueBy(rq.getApplicationStatus().getApplication().getApplicant().getPrevIssuedBy());
		}
		claim.setPreapproved(rq.getPreapprovedFlag());
	}
}
