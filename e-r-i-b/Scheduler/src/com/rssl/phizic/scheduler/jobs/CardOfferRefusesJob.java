package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.ukoClaim.ConfirmClaim;
import com.rssl.phizic.business.loanclaim.ukoClaim.ConfirmPhoneCallback;
import com.rssl.phizic.business.loanclaim.ukoClaim.Confirmable;
import com.rssl.phizic.business.mbuesi.UESICancelLimitMessage;
import com.rssl.phizic.business.mbuesi.UESIService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UESIMessage;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import com.rssl.phizicgate.mobilebank.BkiMessageBean;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.*;
import javax.xml.bind.JAXBException;

/**
 * Джоб получения отказов по кредитным лимитам из МБК и сообщений по расширенным заявкам
 * @author Pankin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CardOfferRefusesJob extends BaseJob implements StatefulJob
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final UESIService UESI_SERVICE = new UESIService();
	private static final String CANCEL_LIMIT = "CANCEL_LIMIT";
	private static final String BKISBOL = "BKISBOL";
	private static final String CLAIM_SEND_SMS_ERROR_MESSAGE = "Ошибка при попытке отправить СМС-сообщение для кредитной заявки. %s";
	private static final String DONT_UNIQ_CLAIM_BY_NUMBER_ERROR_MESSAGE = "Ошибка при обработке сообщения из МБК. В БД ЕРИБ обнаружено более одной заявки с коротким номером \"%s\"";
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			//Сообщения из МБК для обработки
			List<UESIMessage> messages = mobileBankService.getUESIMessagesFromMBK();
			if (CollectionUtils.isEmpty(messages))
				return;
			//Список ID-сообщений для уведомления МБК об их обработке
			List<Long> confirmedMessageIds = new ArrayList<Long>();
			//Список сообщений с отказами по кредитным лимитам
			List<UESICancelLimitMessage> cancelLimitMessages = new ArrayList<UESICancelLimitMessage>();
			//Список номеров заявок для отправки по ним СМС-сообщения клиенту-инициатору заявки
			Map<Long, BkiMessageBean> bkiClaimNumbersWithMessageIdMap = new HashMap<Long, BkiMessageBean>();

			for (UESIMessage message : messages)
			{
				LOG.trace("CardOfferRefusesJob: Обработка сообщения от МБК: "+message.getMessageText());
				if (CANCEL_LIMIT.equals(message.getMessageType()))
				{
					try
					{
						cancelLimitMessages.add(new UESICancelLimitMessage(message));
						confirmedMessageIds.add(message.getMessageId());
					}
					catch (BusinessException e)
					{
						LOG.error("Ошибка при добавлении UESI сообщения типа " + CANCEL_LIMIT + ". ID = " + message.getMessageId(), e);
					}
				}
				else if (BKISBOL.equals(message.getMessageType()))
				{
					BkiMessageBean bkiMessage = parseBkiMessage(message.getMessageText());
					if (bkiMessage != null)
						bkiClaimNumbersWithMessageIdMap.put(message.getMessageId(), bkiMessage);
				}
				else
				{
					LOG.error("Неизвестный тип сообщения :" + message.getMessageType() + ". ID = " + message.getMessageId());
				}
			}
			if (CollectionUtils.isNotEmpty(cancelLimitMessages))
				UESI_SERVICE.addList(cancelLimitMessages);

			if (CollectionUtils.isNotEmpty(bkiClaimNumbersWithMessageIdMap.entrySet()))
			{
				//Отправка СМС клиенту
				List<Long> messageIdsList = sendSmsToClaimOwner(bkiClaimNumbersWithMessageIdMap);
				//Добавление ИД сообщений по которым успешно обработан ответ МБК
				if (CollectionUtils.isNotEmpty(messageIdsList))
					confirmedMessageIds.addAll(messageIdsList);
			}

			//Подтверждение получения сообщений унифицированного интерфейса МБК
			if (CollectionUtils.isNotEmpty(confirmedMessageIds))
				mobileBankService.confirmUESIMessages(confirmedMessageIds);
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	private BkiMessageBean parseBkiMessage(String text)
	{
		try
		{
			return JAXBUtils.unmarshalBean(BkiMessageBean.class, text);
		}
		catch (JAXBException e)
		{
			LOG.error("Не удалось разобрать xml сообщения БКИ", e);
			return null;
		}
	}

	/**
	 * Отправка СМС-сообщения клиенту с проверкой по номеру блока
	 * @param bkiClaimNumbersWithMessageIdMap - мапа key-ид сообщения из МБК, value - БКИ-номер заявки для подтверждения
	 * @return  List<Long> - список ИД сообщений из МБК, которые успешно обработали
	 */
	private List<Long> sendSmsToClaimOwner(Map<Long, BkiMessageBean> bkiClaimNumbersWithMessageIdMap) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(bkiClaimNumbersWithMessageIdMap.entrySet()))
			return null;
		LOG.trace("CardOfferRefusesJob: отправка СМС сообщения");
		//Определение минимальной даты начиная с которой будут искаться кредитные заявки
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		int confirmAvailableDays = config.getConfirmAvailableDays();
		Calendar minClaimConfirmationDate = DateHelper.getOnlyDate(DateHelper.addDays(Calendar.getInstance(), -confirmAvailableDays));
		//Список ИД сообщений, по которым обнаружены заявки на кредит и отправлены сообщения с кодом подтверждения
		List<Long> messageIdsList = new ArrayList<Long>();

		for (Map.Entry<Long, BkiMessageBean> bkiMessageEntry : bkiClaimNumbersWithMessageIdMap.entrySet())
		{
			BkiMessageBean bkiMessage = bkiMessageEntry.getValue();

			//Номер заявки пришедший из МБК
			String bkiClaimNumber = bkiMessage.getQuery();
			//Должна возвратиться одна заявка, но т.к. остается вероятность того, что найдутся несколько заявок с одинаковым коротким номером, то обрабатываем список
			List<ExtendedLoanClaim> loanClaimList = businessDocumentService.findLoanClaimsByNumbers(bkiClaimNumber, minClaimConfirmationDate);
			//Обнаружено более одной заявки
			if (loanClaimList.size() > 1)
			{
				LOG.error(String.format(DONT_UNIQ_CLAIM_BY_NUMBER_ERROR_MESSAGE, bkiClaimNumber));
				continue;
			}
			//Не обнаружили заявку с таким номером в БД ЕРИБ, т.е. это вероятно заявка другого блока
			if (CollectionUtils.isEmpty(loanClaimList))
			{
				LOG.trace("CardOfferRefusesJob: не обнаружена кредитная заявка с номером БКИ="+bkiClaimNumber);
				continue;
			}
			//"OperationUID" заявки строится из перевернутого значения счетчика + время в миллисек + номера блока.
			//Отсортировываем заявки предназначенные НЕ для текущего блока. Если это заявка не нашего блока, то пусть её обработает джоб другого блока.
			//Номер блока, в котором работает джоб
			Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			ExtendedLoanClaim claim = loanClaimList.get(0);
			LOG.trace("CardOfferRefusesJob: обнаружена кредитная заявка ИД="+claim.getId());
			if (claim.getOperationUID().endsWith(StringHelper.appendLeadingZeros(nodeNumber.toString(), 2)))
			{
				//Отсортировываем по состоянию заявки
				if ("WAIT_TM".equals(claim.getState().getCode()))
					continue;
				//Отсортировываем по сохраненному в хвостах номеру (БКИ_ХХХХХ)
				String claimNumber = claim.getBkiClaimNumber();
				if (StringHelper.isEmpty(claimNumber) || !bkiClaimNumber.equalsIgnoreCase(claimNumber))
					continue;
				//Ну если сюда все-таки добрались, то, скорее всего, действительно это сообщение для заявка на кредит нашего блока
				//Отправляем клиенту СМС с кодом подтверждения, взятой из заявки
				if (isSMSPasswordSended(claim, bkiMessage.getPhone()))
				{
					LOG.trace("CardOfferRefusesJob: СМС сообщение отправлено");
					Long messageId = bkiMessageEntry.getKey();
					//запоминаем ИД сообщения из МБК, по которому успешно отправили сообщение
					messageIdsList.add(messageId);
				}
				else
					LOG.trace("CardOfferRefusesJob: СМС сообщение не отправлено");
			}
		}
		return messageIdsList;
	}

	/**
	 * Отправка клиенту СМС-сообщения с кодом подтверждения
	 *
	 * @param claim - заявка по которой клиент подтверждает действие паролем
	 * @param phone телефон, на который производится отправка (пришедший из МБК)
	 * @return true - если сообщение отправлено
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private boolean isSMSPasswordSended(ExtendedLoanClaim claim, String phone) throws BusinessLogicException, BusinessException
	{
		try
		{
			if (claim == null || StringHelper.isEmpty(claim.getConfirmPassword()))
			{
				String errorMessage = String.format(CLAIM_SEND_SMS_ERROR_MESSAGE, claim == null ? "Документ не обнаружен." : "ID="+claim.getId());
				LOG.error(errorMessage);
				return false;
			}

			Confirmable confirmable = getConfirmObject(claim);
			CallBackHandlerSmsImpl callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setConfirmableObject(confirmable);
			callBackHandler.setLogin(claim.getOwner().getLogin());
			//Пароль уже лежит в хвостах заявки
			callBackHandler.setPassword(claim.getConfirmPassword());
			//Номер телефона из МБК
			callBackHandler.setPhoneNumber(phone);
			//Подтверждение запроса в БКИ при заказе обратного звонка
			callBackHandler.setOperationType(OperationType.CALLBACK_BCI_CONFIRM_OPERATION);
			callBackHandler.setUseRecipientMobilePhoneOnly(true);
			callBackHandler.proceed();
		}
		catch (Exception e)
		{
			LOG.error(e);
			return false;
		}
		return true;
	}

	private Confirmable getConfirmObject(ExtendedLoanClaim claim)
	{
		return ExtendedLoanClaim.CONFIRM_GUEST_ACTION.equals(claim.getActionSign()) ? new ConfirmClaim(claim.getId()) : new ConfirmPhoneCallback(claim.getId());
	}
}
