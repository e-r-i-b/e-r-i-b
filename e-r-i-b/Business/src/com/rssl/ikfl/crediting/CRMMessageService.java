package com.rssl.ikfl.crediting;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Сервис интеграции с CRM
 *
 * @ author: Gololobov
 * @ created: 04.03.15
 * @ $Author$
 * @ $Revision$
 */
public class CRMMessageService
{
	private final CRMMessageBuilder messageBuilder = new CRMMessageBuilder();
	private final CRMMessageSender messageSender = new CRMMessageSender();
	private final CRMMessageLogger messageLogger = new CRMMessageLogger();
	private static final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Создание задания на обрантый звонок
	 * @param claim - частично заполненная кредитная заявка
	 * @throws Exception
	 */
	public void createNewCallbackTask(ExtendedLoanClaim claim) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.makePhoneCallback(claim);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * Создание задания на обзвон в CRM
	 *
	 * @param claim - частично заполненная кредитная заявка
	 * @param employee
	 * @throws Exception
	 */
	public void createNewCallTask(ExtendedLoanClaim claim, Employee employee) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.makePhoneCall(claim, employee);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * Поиск существующих в ETSM заявок клиента по запросу из ЕРИБ
	 *
	 * @param person - Клиент
	 * @throws Exception
	 */
	public void searchApplicationRequest(Person person) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.searchApplicationRequestMessage(person);
		sendMessageToCRMForLoanClaim(crmMessage);
	}


	/**
	 * Создание новой кредитной заявки в CRM
	 * @param claim - кредитная заявка
	 * @throws Exception
	 */
	public void createNewExtendedLoanClaim(ExtendedLoanClaim claim) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.makeNewClaim(claim);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * Инициация создания новой заявки. Отправка заявки из ЕРИБ в ETSM в «ручном режиме»
	 */
	public void initiationCreateNewLoanClaim(ExtendedLoanClaim claim) throws Exception
	{
		//Отправка редитной заявки в ETSM
		CRMStateType state = CRMStateType.OK_STATUS;
		//В этих состояниях не все, обязательные для передачи в ETSM, данные заполнены
		if ("INITIAL".equals(claim.getState().getCode()) ||
			"INITIAL2".equals(claim.getState().getCode()))
			state = CRMStateType.INNER_ERROR;
		else
			state = sendLoanClaimToETSM(claim);
		//Ответное сообщение в CRM об изменении статуса кредитной заявки или произошедшей ошибке
		updateLoanClaimStatus(state, claim);
	}


	/**
	 * Отправка в ETSM квитанции о доставке в ответ на запрос согласия на оферту
	 * @param statusCode Код статуса. «-1» –  техническая ошибка доставки, «0» – успешная обработка обработка, «1» – бизнес-ошибка.
	 * @param error Описание ошибки
	 * @param rqUID исходного сообщения.
	 * @param rqTm исходного сообщения.
	 */
	public void sendOfferTicket(String rqUID ,Calendar rqTm,int statusCode, String error) throws Exception
	{
		///Передача в CRM информации о статусе заявки в ЕРИБ. Ответа от CRM после этого не предусматривается.
		CRMMessage crmMessage = messageBuilder.makeOfferTicket(rqUID, rqTm, statusCode, error);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * Передача в CRM информации об изменении статуса заявки в ЕРИБ. Ответа от CRM после этого не предусматривается.
	 * @param state
	 * @param claim
	 * @throws Exception
	 */
	public void updateLoanClaimStatus(CRMStateType state, ExtendedLoanClaim claim) throws Exception
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		//Если отправка статусов в CRM доступна
		if (config.isSendUpdStatusAvailable())
		{
			//Передача в CRM информации о статусе заявки в ЕРИБ. Ответа от CRM после этого не предусматривается.
			CRMMessage crmMessage = messageBuilder.makeUpdateClaimStatus(state, claim);
			sendMessageToCRMForLoanClaim(crmMessage);
		}
	}

	/**
	 * Отправка заявки из ЕРИБ в ETSM. Если происходит ошибка, то в CRM необходимо вернуть ответ об ошибке
 	 * @param claim
	 */
	private CRMStateType sendLoanClaimToETSM(ExtendedLoanClaim claim)
	{
		try
		{
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
			executor.initialize(claim);
			ObjectEvent event = new ObjectEvent(DocumentEvent.ETSM_SEND, ObjectEvent.SYSTEM_EVENT_TYPE);
			executor.fireEvent(event);
			//Обновление заявки на кредит в БД
			businessDocumentService.addOrUpdate(claim);
			return CRMStateType.OK_STATUS;
		}
		catch (BusinessLogicException e)
		{
			log.error(e);
			return CRMStateType.INNER_ERROR;
		}
		catch (BusinessException e)
		{
			log.error(e);
			return CRMStateType.INNER_ERROR;
		}
	}

	/**
	 * Отправка сообщения в CRM
	 * @param crmMessage
	 * @throws JMSException
	 * @throws NamingException
	 */
	private void sendMessageToCRMForLoanClaim(CRMMessage crmMessage) throws JMSException, NamingException
	{
		messageSender.sendMessageToCRMForLoanClaim(crmMessage);
		//Запоминаем запрос к CRM в Журнале Сообщений
		messageLogger.logMessage(crmMessage);
	}

    /**
     * Отказ от оферты
     * @param srcRqUID Идентификатор исходного сообщения запрос согласия на оферту (ETSM-ЕРИБ);
     * @param offerId Идентификатор заявки в ЕРИБ
     */
    public void sendRefuseConsumerProductOfferResultRq(String srcRqUID, String offerId) throws Exception
    {
        CRMMessage message = messageBuilder.consumerProductOfferResultRq(srcRqUID, offerId, null, null, null, null, false);
        sendMessageToCRMForLoanClaim(message);
    }

	/**
	 * @param srcRqUID Идентификатор исходного сообщения запрос согласия на оферту (ETSM-ЕРИБ);
	 * @param offerId Идентификатор заявки в ЕРИБ
	 * @param finalAmount Итоговая сумма кредита в рублях
	 * @param finalPeriodM Итоговый срок кредита в месяцах
	 * @param finalInterestRate Итоговая процентная ставка
	 * @param offerAgreeDate дата и время согласования оферты
	 * @return  CRMMessage
	 * @throws Exception
	 */
	public void sendAgreeConsumerProductOfferResultRq(String srcRqUID,String offerId, BigDecimal finalAmount,
	                                                  Long finalPeriodM, BigDecimal finalInterestRate, Calendar offerAgreeDate) throws Exception
	{
		CRMMessage message = messageBuilder.consumerProductOfferResultRq(srcRqUID, offerId, finalAmount, finalPeriodM, finalInterestRate, offerAgreeDate, true);
		sendMessageToCRMForLoanClaim(message);
	}
}
