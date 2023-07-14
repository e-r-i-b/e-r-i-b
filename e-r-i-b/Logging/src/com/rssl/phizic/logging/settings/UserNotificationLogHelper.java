package com.rssl.phizic.logging.settings;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.UserMessageLogWriter;
import com.rssl.phizic.logging.writers.UserNotificationLogWriter;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class UserNotificationLogHelper
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String NOTIFICATION_ERROR = "�� ������� �������� ���������� � ����� �������� ���������� � ������� ����������";
	/**
	 * ���������� ������ � ��� � ��������� �������� �������
	 * @param loginId - loginId �������
	 * @param type  - ����� ��������� ��������
	 * @param value  - ����� �������� ��������
	 */
	public static void saveNotificationSettings(Long loginId, NotificationInputType type, String value)
	{
		try
		{
			UserNotificationLogRecord entry = new UserNotificationLogRecord();
			entry.setLoginId(loginId);
			entry.setType(type);
			entry.setAdditionDate(Calendar.getInstance());
			entry.setValue(value);
			writeToActionLog(entry);
		}
		catch (Throwable t)
		{
			//������� ����������� �� ������ �������� ������ �������.
			log.error(NOTIFICATION_ERROR, t);
		}
	}

	private static void writeToActionLog(UserNotificationLogRecord entry)
	{
		if(entry == null)
			return;

		MessageLogConfig config = ConfigFactory.getConfig(MessageLogConfig.class);
		if (!config.isUserNotificationLogging())
			return;
		UserNotificationLogWriter writer = config.getUserNotificationWriter();
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
