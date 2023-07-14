package com.rssl.phizic.messaging.ermb.jms;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.messaging.MessagingConfig;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ermb.ErmbSmsContext;
import com.rssl.phizic.messaging.ermb.SendSmsRequest;
import com.rssl.phizic.messaging.ermb.SendSmsWithImsiRequest;
import com.rssl.phizic.messaging.ermb.TransportMessageSerializer;
import com.rssl.phizic.messaging.mail.SmsMessage;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.jms.JmsService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

import java.util.*;
import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.TRANSPORT_MESSAGE_CQF;
import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.TRANSPORT_MESSAGE_QUEUE;

/**
 * @author EgorovaA
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Транспортный сервис для ЕРМБ (отправка с использованием jms)
 */
public class JmsTransportService implements SmsTransportService
{
	private final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final JmsService jmsService = new JmsService();

	private final TransportMessageSerializer requestSerializer = new TransportMessageSerializer();

	public void sendSms(String phoneAsString, String text, String textToLog, Long priorityAsLong) throws IKFLMessagingException
	{
		if (StringHelper.isEmpty(text))
			throw new IllegalArgumentException("Не указан текст сообщения");
		if (StringHelper.isEmpty(textToLog))
			throw new IllegalArgumentException("Не указан текст сообщения для логирования");

		PhoneNumber phone = PhoneNumber.fromString(phoneAsString);
		int priority = priorityAsLong.intValue();

		try
		{
			UUID requestID = new RandomGUID().toUUID();
			Calendar requestTime = Calendar.getInstance();
			SendSmsRequest request = buildSendSmsRequest(requestID, requestTime, phone, text, priority);
			SendSmsRequest requestToLog = buildSendSmsRequest(requestID, requestTime, phone, textToLog, priority);

			String requestXML = requestSerializer.marshallRequest(request);

			boolean ermbSmsRequestIdSettingEnabled = ConfigFactory.getConfig(MessagingConfig.class).isErmbSmsRequestIdSettingEnabled();
			if (ermbSmsRequestIdSettingEnabled)
				jmsService.sendMessageToQueue(requestXML, TRANSPORT_MESSAGE_QUEUE, TRANSPORT_MESSAGE_CQF, null, StringHelper.getNullIfNull(request.getErmbCorrelationID()));
			else
				jmsService.sendMessageToQueue(requestXML, TRANSPORT_MESSAGE_QUEUE, TRANSPORT_MESSAGE_CQF, null, null);

			writeMessageLog(requestToLog);
		}
		catch (JAXBException e)
		{
			throw new IKFLMessagingException(e);
		}
		catch (JMSException e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	public void sendSms(String toPhone, TranslitMode translit, String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		if (TranslitMode.TRANSLIT == translit)
		{
			//noinspection AssignmentToMethodParameter
			text = StringUtils.translit(text);
			//noinspection AssignmentToMethodParameter
			textToLog = StringUtils.translit(textToLog);
		}
		sendSms(toPhone, text, textToLog, priority);
	}

	/**
	 * Отправка смс с проверкой IMSI
	 * @param messageInfo информация о сообщении
	 * @param mbSystemId ID ИКФЛ в АС "Мобильный Банк"
	 * @param toPhones телефоны
	 * @return мап (номер телефона - ошибка)
	 * @throws IKFLMessagingException
	 */
	public Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo, Long priorityAsLong, String... toPhones) throws IKFLMessagingException
	{
		int priority = priorityAsLong.intValue();

		// Мапа с номерами телефонов, по которым не получилось отправить смс, и ошибкой. Ключ - номер, значение - ошибка
		Map<String, SendMessageError> errorByPhones = new HashMap<String, SendMessageError>();
		// Мапа с данными запроса. Ключ - UID запроса, значение - номер телефона
		Map<String, String> requestInfo = new HashMap<String, String>();

		MessagingConfig config = ConfigFactory.getConfig(MessagingConfig.class);
		// 1. Отправляем смс с проверкой IMSI
		for (String phoneAsString : toPhones)
		{
			log.debug("Отправка СМС с проверкой IMSI для номера телефона '" + phoneAsString + "' ..."+messageInfo.getText());
			try
			{
				UUID requestID = new RandomGUID().toUUID();
				Calendar requestTime = Calendar.getInstance();
				PhoneNumber phone = PhoneNumber.fromString(phoneAsString);
				SendSmsWithImsiRequest request = buildSendSmsWithImsiRequest(requestID, requestTime, phone, messageInfo.getText(), priority);
				SendSmsWithImsiRequest requestToLog = buildSendSmsWithImsiRequest(requestID, requestTime, phone, messageInfo.getTextToLog(), priority);

				String requestXML = requestSerializer.marshallRequest(request);
				if (config.isErmbSmsRequestIdSettingEnabled())
				{
					UUID ermbCorrelationID = request.getErmbCorrelationID();
					jmsService.sendMessageToQueue(requestXML, TRANSPORT_MESSAGE_QUEUE, TRANSPORT_MESSAGE_CQF, null, StringHelper.getNullIfNull(ermbCorrelationID));
				}
				else
					jmsService.sendMessageToQueue(requestXML, TRANSPORT_MESSAGE_QUEUE, TRANSPORT_MESSAGE_CQF, null, null);
				requestInfo.put(requestID.toString(), phoneAsString);

				writeMessageLog(requestToLog);
			}
			catch (JMSException e)
			{
				errorByPhones.put(phoneAsString, SendMessageError.error);
				log.error(e.getMessage(), e);
			}
			catch (JAXBException e)
			{
				errorByPhones.put(phoneAsString, SendMessageError.error);
				log.error(e.getMessage(), e);
			}
		}

		// 2. Проверка IMSI
		// Копируем UID запросов. После получения результатов проверки их надо удалить.
		Set<String> resCheckList = new HashSet<String>(requestInfo.keySet());
		try
		{
			Long delayTime = config.getImsiCheckDelayTime();
			for (int i=0; i < config.getNumberOfIMSICheckTries(); i++)
			{
				List<ErmbCheckIMSIResult> resultList = getResult(requestInfo.keySet());
				for (ErmbCheckIMSIResult result : resultList)
				{
					String rqUid = result.getSmsRqUid();
					if (!result.getResult())
						errorByPhones.put(requestInfo.get(rqUid), SendMessageError.error);
					requestInfo.remove(rqUid);
				}
				if (requestInfo.isEmpty())
					break;
				Thread.sleep(delayTime);
			}
		}
		catch (InterruptedException e)
		{
			log.error("InterruptedException:" + e.getMessage());
		}
		finally
		{
			// Если по каким-то запросам не получили результат проверки, телефоны этих запросов добавляем в мапу с ошибками
			for (String phone : requestInfo.values())
			{
				errorByPhones.put(phone, SendMessageError.error);
			}
			// Удаляем результаты проверки всех отправленных в этой пачке запросов
			removeResultList(resCheckList);
		}
		return errorByPhones;
	}

	public SmsMessage receive() throws IKFLMessagingException
	{
		throw new UnsupportedOperationException();
	}

	private SendSmsRequest buildSendSmsRequest(UUID requestID, Calendar requestTime, PhoneNumber phone, String text, int priority)
	{
		SendSmsRequest request = new SendSmsRequest();
		request.setRqVersion(SendSmsRequest.VERSION);
		request.setRqUID(requestID);
		request.setRqTime(requestTime);
		request.setPhone(phone);
		request.setText(text);
		request.setPriority(priority);
		request.setErmbCorrelationID(ErmbSmsContext.getIncomingSMSRequestUID());
		return request;
	}

	private SendSmsWithImsiRequest buildSendSmsWithImsiRequest(UUID requestID, Calendar requestTime, PhoneNumber phone, String text, int priority)
	{
		SendSmsWithImsiRequest request = new SendSmsWithImsiRequest();
		request.setRqVersion(SendSmsWithImsiRequest.VERSION);
		request.setRqUID(requestID);
		request.setRqTime(requestTime);
		request.setPhone(phone);
		request.setText(text);
		request.setPriority(priority);
		request.setErmbCorrelationID(ErmbSmsContext.getIncomingSMSRequestUID());
		return request;
	}

	/**
	 * Найти в базе список результатов проверки IMSI по UID запроса
	 * @param smsRqUidList - список UID запросов
	 * @return результаты проверки 
	 */
	public List<ErmbCheckIMSIResult> getResult(final Set<String> smsRqUidList) throws IKFLMessagingException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ErmbCheckIMSIResult>>()
				{
					public List<ErmbCheckIMSIResult> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.messaging.ermb.jms.getResultBySmsId");
						query.setParameterList("smsRqUidList", smsRqUidList);
						return (List<ErmbCheckIMSIResult>) query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	/**
	 * Удалить результаты проверки IMSI из базы
	 * @param smsRqUidList - список UID запросов
	 */
	private void removeResultList(final Set<String> smsRqUidList) throws IKFLMessagingException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateActionStateless<Void>()
			{
				public Void run(StatelessSession session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.messaging.ermb.jms.removeResultList");
					query.setParameterList("smsRqUidList", smsRqUidList);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	private void writeMessageLog(SendSmsRequest request)
	{
		try
		{
			writeLogEntry(request.getRqUID().toString(), requestSerializer.marshallRequest(request), request.getRequestType());
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}


	private void writeMessageLog(SendSmsWithImsiRequest request)
	{
		try
		{
			writeLogEntry(request.getRqUID().toString(), requestSerializer.marshallRequest(request), request.getRequestType());
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}

	private void writeLogEntry(String rqUid, String messageRequest, String messageType) throws Exception
	{
		MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		logEntry.setMessageRequestId(rqUid);
		logEntry.setMessageRequest(messageRequest);
		logEntry.setMessageType(messageType);
		logEntry.setApplication(Application.ErmbTransportChannel);
		logEntry.setSystem(System.ERMB_SOS);

		messageLogWriter.write(logEntry);
	}
}
