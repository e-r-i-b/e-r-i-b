package com.rssl.phizic.csa.ejb;

import com.rssl.phizic.business.ermb.sms.messaging.SmsSenderUtils;
import com.rssl.phizic.common.type.ErmbProfileIdentity;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoException;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoLogicException;
import com.rssl.phizic.csa.exceptions.CSASmsProcessingException;
import com.rssl.phizic.csa.exceptions.CSASmsProcessingLogicException;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.*;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import org.apache.commons.logging.Log;
import org.xml.sax.SAXException;

import java.text.ParseException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для работы с очередями ЦСА
 */
public abstract class CSAEjbListenerBase implements MessageDrivenBean, MessageListener
{
	protected static final JmsService jmsService = new JmsService();

	protected static final SOSMessageHelper sosMessageHelper = new SOSMessageHelper();
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	public void ejbCreate() throws EJBException
	{
		try
		{
			LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
		}
		catch (Exception e)
		{
			throw new EJBException(e);
		}
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.CsaErmbListener);
		String text = getText(message);
		//Если упали при получении сообщения, ничего больше не делаем.
		if (StringHelper.isEmpty(text))
		{
			ApplicationInfo.setDefaultApplication();
			return;
		}

		try
		{
			MQInfo mqInfo = getMQInfo(text);

			jmsService.sendMessageToQueue(text, mqInfo.getQueueName(), mqInfo.getFactoryName(), null, null);
		}
		catch (CSAGetNodeInfoLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (CSAGetNodeInfoException e)
		{
			log.error("Ошибка получения информации о блоке пользователя.", e);
		}
		catch (CSASmsProcessingLogicException e)
		{
			log.error(e.getMessage(), e);
			sendErrorMessage(e.getPhoneNumber(), e.getMessage());
		}
		catch (CSASmsProcessingException e)
		{
			log.error(e.getMessage(), e);
			sendErrorMessage(e.getPhoneNumber(), "Операция временно недоступна. Пожалуйста, повторите попытку позже.");
		}
		catch (SAXException e)
		{
			log.error("Ошибка разбора xml-сообщения.", e);
		}
		catch (JMSException e)
		{
			log.error("Ошибка при работе с JMS.", e);
		}
		catch (JAXBException e)
		{
			writeMessageToLog(text, null);
			log.error("Ошибка десериализации сообщения.", e);
		}
		catch (ParseException e)
		{
			writeMessageToLog(text, null);
			log.error("Ошибка разбора сообщения.", e);
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки сообщения в очередь.", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private String getText(Message message)
	{
		try
		{
			TextMessage textMessage = (TextMessage) message;
			return textMessage.getText();
		}
		catch (JMSException e)
		{
			log.error("Ошибка при работе с JMS.", e);
			return "";
		}
	}

	protected void writeMessageToLog(String message, UUID requestId)
	{
		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry entry = MessageLogService.createLogEntry();

			entry.setApplication(ApplicationInfo.getCurrentApplication());
			entry.setSystem(System.CSA_MQ);
			entry.setMessageRequest(message);
			entry.setMessageRequestId(requestId == null ? "empty_id" : requestId.toString());
			entry.setMessageType(getMessageType());

			if (writeAvailable())
			{
				writer.write(entry);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при записи в журнал сообщений. Сообщение: " + message, e);
		}
	}

	protected abstract String getMessageType();

	protected abstract boolean writeAvailable();

	private void sendErrorMessage(String phoneNumber, String message)
	{
		try
		{
			SmsSenderUtils.notifyClientMessage(phoneNumber, message);
		}
		catch (Exception e)
		{
			log.error("Ошибка при отправке сообщения об ошибке на номер: " + phoneNumber, e);
		}
	}

	protected UserInfo fillUserInfo(ErmbProfileIdentity person)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname(person.getFirstName());
		userInfo.setSurname(person.getSurName());
		userInfo.setPatrname(person.getPatrName());
		userInfo.setBirthdate(person.getBirthDay());
		String passport = StringHelper.getEmptyIfNull(person.getIdentityCard().getIdSeries()) + StringHelper.getEmptyIfNull(person.getIdentityCard().getIdNum());
		userInfo.setPassport(passport);
		userInfo.setCbCode(person.getTb());

		return userInfo;
	}

	/**
	 * Получить информацию об MQ блока
	 * @param message сообщение
	 * @return MQInfo
	 */
	protected abstract MQInfo getMQInfo(String message) throws Exception;
}
