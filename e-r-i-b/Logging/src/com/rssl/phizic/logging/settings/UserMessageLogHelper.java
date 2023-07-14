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
	private static final String PUSH_MESSAGE_STATISTIC_ERROR = "�� ������� �������� ���������� � push-��������� � ������� ����������";
	private static final String MAIL_MESSAGE_STATISTIC_ERROR = "�� ������� �������� ���������� � email-��������� � ������� ����������";

	/**
	 * ���������� ������ � �������� ����������� �� push � ������ ���������� �������� ���������� ��������
	 * @param creationDate - ���� �������� ����������
	 * @param clientId - ������������� ������������� �������
	 * @param loginId - loginId �������, �������� ���������� ����������
	 * @param typeCode  - ��� ���������
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
			//������� ����������� �� ������ �������� ������ �������.
			log.error(PUSH_MESSAGE_STATISTIC_ERROR, t);
		}
	}

	/**
	 * ���������� ������ � �������� ����������� �� email � ������ ���������� �������� ���������� ��������
	 * @param loginId - loginId �������, �������� ���������� ����������
	 * @param messageId  - �������������� ���������
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
			//������� ����������� �� ������ �������� ������ �������.
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
			//������� ����������� �� ������ �������� ������ �������.
			log.error(t);
		}
	}
}
