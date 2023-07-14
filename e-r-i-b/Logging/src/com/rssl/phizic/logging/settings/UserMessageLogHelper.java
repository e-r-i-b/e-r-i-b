package com.rssl.phizic.logging.settings;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogConfig;
import com.rssl.phizic.logging.push.PushEventType;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.UserMessageLogWriter;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class UserMessageLogHelper
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String PUSH_MESSAGE_STATISTIC_ERROR = "Ќе удалось записать информацию о push-сообщении в таблицу статистики";
	private static final String MAIL_MESSAGE_STATISTIC_ERROR = "Ќе удалось записать информацию о email-сообщении в таблицу статистики";

	/**
	 * —охрани€ем запись о доставке уведомлени€ по push в журнал статистики отправки оповещений клиентам
	 * @param creationDate - дата отправки оповещени€
	 * @param clientId - зашифрованный индетификатор клиента
	 * @param loginId - loginId клиента, которому отправл€ем оповещение
	 * @param typeCode  - тип сообщени€
	 */
	public static void savePushEventToLog(Calendar creationDate, String clientId, Long loginId, PushEventType typeCode){

		try
		{
			UserMessageLogRecord logEntry = new UserMessageLogRecord();
			logEntry.setAdditionDate(creationDate);
			logEntry.setType(MessageType.push);
			logEntry.setMessageId(clientId);
			logEntry.setLoginId(loginId);
			logEntry.setTypeCode(typeCode);
			writeToActionLog(logEntry);
		}
		catch (Throwable t)
		{
			//падение логировани€ не должно нарушать работу системы.
			log.error(PUSH_MESSAGE_STATISTIC_ERROR, t);
		}
	}

	/**
	 * —охрани€ем запись о доставке уведомлени€ по email в журнал статистики отправки оповещений клиентам
	 * @param loginId - loginId клиента, которому отправл€ем оповещение
	 * @param messageId  - идентификатора сообщени€
	 */
	public static void saveMailNotificationToLog(Long loginId, String messageId)
	{
		try
		{
			UserMessageLogRecord logEntry = new UserMessageLogRecord();
			logEntry.setMessageId(messageId);
			logEntry.setLoginId(loginId);
			logEntry.setType(MessageType.email);
			logEntry.setAdditionDate(Calendar.getInstance());
			writeToActionLog(logEntry);
		}
		catch (Throwable t)
		{
			//падение логировани€ не должно нарушать работу системы.
			log.error(MAIL_MESSAGE_STATISTIC_ERROR, t);
		}
	}


	private static void writeToActionLog(UserMessageLogRecord entry)
	{
		if(entry == null)
			return;

		MessageLogConfig config = ConfigFactory.getConfig(MessageLogConfig.class);
		if (!config.isUserMessageLogging())
			return;
		UserMessageLogWriter writer = config.getUserMessageWriter();
		try
		{
			if (writer != null)
			{
				writer.write(entry);
			}
		}
		catch (Throwable t)
		{
			//падение логировани€ не должно нарушать работу системы.
			log.error(t);
		}
	}
}
