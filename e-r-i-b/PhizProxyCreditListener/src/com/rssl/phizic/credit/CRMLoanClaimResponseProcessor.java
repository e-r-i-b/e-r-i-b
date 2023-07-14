package com.rssl.phizic.credit;

import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.etsm.offer.OfferPriorService;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.loanclaim.EtsmConfig;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.SPNameType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.InitiateConsumerProductOfferRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferResultTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.jms.JMSException;

/**
 * Обработка ответов от CRM по инициации создания новой заявки
 *
 * @ author: Gololobov
 * @ created: 05.03.15
 * @ $Author$
 * @ $Revision$
 */
public class CRMLoanClaimResponseProcessor extends MessageProcessorBase<PhizProxyCreditEjbMessage>
{
	private static final String ERROR_ERIBSENDETSMAPPLRQ_MESSAGE = "\"Пришел запрос \\\"ERIBSendETSMApplRq\\\" rqUID=%s не от \\\"CRM\\\" SPName=%s\"";
	private static final CRMMessageService crmMessageService = new CRMMessageService();
	private static final OfferPriorService offerPriorService = new OfferPriorService();
	private static final SimpleService simpleService = new SimpleService();
	private final JmsService jmsService = new JmsService();


	/**
	 * ctor
	 * @param module - модуль
	 */
	public CRMLoanClaimResponseProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(PhizProxyCreditEjbMessage xmlRequest)
	{
		try
		{
			//Инициация создания новой заявки
			if (xmlRequest.getEribSendETSMApplRq() != null)
			{
				ERIBSendETSMApplRq request = xmlRequest.getEribSendETSMApplRq();
				String rqUID = request.getRqUID();
				//Ждем запрос только от CRM
				if (!request.getSPName().equals(SPNameType.CRM))
					throw new RuntimeException(String.format(ERROR_ERIBSENDETSMAPPLRQ_MESSAGE, rqUID, request.getSPName().value()));

				if (request.getApplication() != null && StringHelper.isNotEmpty(request.getApplication().getNumber()))
				{
					processCRMRq(xmlRequest.getMessage(), request.getApplication().getNumber());
				}
			}
			else if (xmlRequest.getEribSendETSMApplRq19() != null)
			{
				com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ERIBSendETSMApplRq request = xmlRequest.getEribSendETSMApplRq19();
				String rqUID = request.getRqUID();
				//Ждем запрос только от CRM
				if (!request.getSPName().equals(SPNameType.CRM))
					throw new RuntimeException(String.format(ERROR_ERIBSENDETSMAPPLRQ_MESSAGE, rqUID, request.getSPName().value()));

				if (request.getApplication() != null && StringHelper.isNotEmpty(request.getApplication().getNumber()))
				{
					processCRMRq(xmlRequest.getMessage(), request.getApplication().getNumber());
				}
			}

		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		//Информация по существующим заявкам клиента(ответ на SearchApplicationRq)
		if (xmlRequest.getSearchApplicationRs() != null)
			processSearchApplicationRs(xmlRequest.getMessage(), xmlRequest.getSearchApplicationRs());
		//Запрос согласия на оферт
		if (xmlRequest.getInitiateConsumerProductOfferRq() != null)
			processInitiateConsumerProductOfferRq(xmlRequest.getMessage(), xmlRequest.getInitiateConsumerProductOfferRq());
		//Квитанция, отправляемая в ответ на запрос подтверждения оферты (offerResultTicket)
		if (xmlRequest.getOfferResultTicket() != null)
			processOfferResultTicket(xmlRequest.getMessage(), xmlRequest.getOfferResultTicket());
	}

	private void processSearchApplicationRs(String requestXML, SearchApplicationRs rquest)
	{
	}

	private void processOfferResultTicket(String requestXML, OfferResultTicket rquest)
	{

	}
	//Обработка оферты
	private void processInitiateConsumerProductOfferRq(String requestXML, InitiateConsumerProductOfferRq rquest)
	{

		String operUID = rquest.getOperUID();
		if (StringHelper.isNotEmpty(operUID))
		{// 1. Если operUID передан значит оферта из ЕРИБ.
			// 1.1 Определяем номер блока
			int length = operUID.length();
			Long blokNumber = Long.valueOf(operUID.substring(length - 2));
			// 1.2 Отправляем
			try
			{
				sendInitiateConsumerProductOfferRqToNode(requestXML, blokNumber);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
			}
			catch (JMSException e)
			{
				log.error(e.getMessage(), e);
			}
		}
		else
		{// 2. Если operUID не передан значит оферта создана в каналах отличных от УКО
			// 2.1 Cохраняем
			Pair<Integer,String>  status = saveOffer(rquest);
			// 2.2 Отправляем ответ о получении
			try
			{
				crmMessageService.sendOfferTicket(rquest.getRqUID(),rquest.getRqTm(),status.getFirst(), status.getSecond());
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	private Pair<Integer,String> saveOffer(InitiateConsumerProductOfferRq rquest)
	{
		OfficeLoanClaim officeClaim = null;
		try
		{
			officeClaim = offerPriorService.getOfficeLoanClaim(rquest.getApplicationNumber());
		}
		catch (BusinessException e)
		{
			return getStatus(-1, e.getMessage());
		}

		if (officeClaim == null)
			return getStatus(1, "Не удалось найди зайвку на кредит созданую в не ЕРИБ по ApplicationNumbe = " + rquest.getApplicationNumber());


		List<InitiateConsumerProductOfferRq.Offer.Alternative> alternatives = rquest.getOffer().getAlternatives();
		if (alternatives.isEmpty())
			return getStatus(1, "Оферта не содержит не одного альтернативного параметры кредита");

		List<OfferOfficePrior> offerList = new ArrayList<OfferOfficePrior>();

		Calendar date = DateHelper.getCurrentDate();
		for (InitiateConsumerProductOfferRq.Offer.Alternative alternative:alternatives)
		{
			OfferOfficePrior offer = new OfferOfficePrior();
			offer.setRqUid(rquest.getRqUID());
			offer.setOfferDate(DateHelper.toDate(date));
			offer.setState("ACTIVE");
			offer.setApplicationNumber(rquest.getApplicationNumber());
			offer.setVisibilityCounter(0L);

			offer.setProductCode(officeClaim.getProductCode());
			offer.setSubProductCode(officeClaim.getSubProductCode());
			offer.setDepartment(officeClaim.getDepartment());
			offer.setCurrency(officeClaim.getCurrency());
			offer.setProductTypeCode(officeClaim.getType());

			offer.setClientCategory(rquest.getOffer().getClientCategory());
			offer.setAltPeriod(alternative.getAltPeriodM());
			offer.setAltAmount(alternative.getAltAmount());
			offer.setAltInterestRate(alternative.getAltInterestRate());
			offer.setAltFullLoanCost(alternative.getAltFullLoanCost());
			offer.setAltAnnuityPayment(alternative.getAltAnnuitentyPayment());
			offer.setAltCreditCardLimit(alternative.getAltCreditCardlimit());
			//ФИО ДР
			offer.setBirthDate(officeClaim.getBirthDay());
			offer.setFirstName(officeClaim.getFirstName());
			offer.setLastName(officeClaim.getSurName());
			offer.setMiddleName(officeClaim.getPatrName());
			//ДУЛ
			offer.setIdType(officeClaim.getType());
			offer.setIdSeries(officeClaim.getDocumentSeries());
			offer.setIdNum(officeClaim.getDocumentNumber());
			offer.setIdIssueDate(officeClaim.getPassportIssueDate());
			offer.setIdIssueBy(officeClaim.getPassportIssueBy());

			offer.setTypeOfIssue(officeClaim.getTypeOfIssue());
			if (StringHelper.isNotEmpty(officeClaim.getAccountNumber()))
				offer.setAccountNumber(officeClaim.getAccountNumber());
			else
				offer.setAccountNumber(officeClaim.getCardNumber());

			offer.setRegistrationAddress(officeClaim.getRegistrationAddresses());
			offerList.add(offer);
		}
		try
		{
			simpleService.addOrUpdateList(offerList,ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
		}
		catch (BusinessException e)
		{
			return getStatus(-1, e.getMessage());
		}
		return getStatus(0,"");
	}

	private Pair<Integer,String> getStatus(int statusCode, String errorDesk)
	{
		Pair<Integer,String> status = new Pair<Integer, String>();
		status.setFirst(statusCode);
		status.setSecond(errorDesk);
		log.error(errorDesk);
		return status;
	}

	private void sendInitiateConsumerProductOfferRqToNode(String requestXML, Long nodeId) throws GateException, JMSException
	{
		//Определяем блок ЕРИБа и кладём заявку в JMS-очередь блока
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);

		NodeInfo nodeInfo = nodeInfoConfig.getNode(nodeId);
		if (nodeInfo == null)
			throw new ConfigurationException("Не найден блок " + nodeId);

		MQInfo dictionaryMQ = nodeInfo.getDictionaryMQ();
		jmsService.sendObjectToQueue(requestXML, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), ETSMLoanClaimConstants.INITIATE_CONSUMER_JMS_TYPE, null);
	}

	private String processCRMRq(String requestXML, String operUID) throws GateException, JMSException
    {
        if (StringHelper.isEmpty(operUID) || StringHelper.isEmpty(operUID.substring(operUID.length()-2)))
            throw new IllegalArgumentException("Не указан номер заявки.");

        //Определяем блок ЕРИБа и кладём заявку в JMS-очередь блока
        sendToNode(Long.valueOf(StringHelper.removeLeadingZeros(operUID.substring(operUID.length()-2))) , requestXML, ETSMLoanClaimConstants.ERIB_SEND_ETSM_TYPE);
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
}
