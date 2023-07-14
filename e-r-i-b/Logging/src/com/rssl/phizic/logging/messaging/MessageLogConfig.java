package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.LogLevelConfig;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.logging.writers.StatisticLogWriter;
import com.rssl.phizic.logging.writers.UserMessageLogWriter;
import com.rssl.phizic.logging.writers.UserNotificationLogWriter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author eMakarov
 * @ created 26.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class MessageLogConfig extends LogLevelConfig
{
	public static final String MAIN_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.MessageLogWriter";
	public static final String BACKUP_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.MessageLogWriter.backup";
	public static final String PUSH_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.PushStatisticLogWriter";
	public static final String USER_MESSAGE_LOG_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.UserMessageLogWriter";
	public static final String USER_MESSAGE_LOGGING_ON_KEY = "com.rssl.phizic.logging.writers.UserMessageLogging.on";
	public static final String USER_NOTIFICATION_LOG_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.UserNotificationLogWriter";
	public static final String USER_NOTIFICATION_LOGGING_ON_KEY = "com.rssl.phizic.logging.writers.UserNotificationLogging.on";
	public static final String DB_INSTANCE_NAME = "com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName";
	public static final String JMS_QUEUE_NAME = "com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName";
	public static final String JMS_QUEUE_FACTORY_NAME = "com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName";
	public static final String EXCLUDED_MESSAGES_PROPERTY_KEY = Constants.MESSAGE_LOG_PREFIX + "excluded.messages";

	protected MessageLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ���������� ������(���/����) ������������ ����������� ��� ������
	 * @param systemName - �������� ������
	 * @return ������ ������������ �����������
	 */
	public abstract boolean getLogState(System systemName);

	/**
	 * ���������� ������(���/����) ������������ ����������� ��� ������
	 * @param systemName - �������� ������
	 * @return ������ ������������ �����������
	 */
	public abstract boolean getExtendedLogState(System systemName);

	/**
	 * @return �������� writer
	 */
	public abstract MessageLogWriter getMainWriter();

	/**
	 * @return ��������� writer
	 */
	public abstract MessageLogWriter getBackupWriter();

	/**
	 * @return ����������� ��� DBWriter
	 */
	public abstract String getDbWriterInstanceName();

	/**
	 * @return name ������� ��� JMSWriter
	 */
	public abstract String getJMSQueueName();

	/**
	 * @return name ������� �������� ��� ������������ �����������
	 */
	public abstract String getJMSQueueFactoryName();

	/**
	 * @return ������ ����������� ��� ����������� ���������
	 */
	public abstract List<Pattern> getExcludedMessages();

	/**
	 * @return ������� ��� ���������� �� �����
	 */
	public abstract StatisticLogWriter getPushStatisticWriter();

	/**
	 * @return ������� ��� ����������� �������� ���������� ������������
	 */
	public abstract UserMessageLogWriter getUserMessageWriter();

	/**
	 * @return ������� ��� ����������� ��������� �������� ����������
	 */
	public abstract UserNotificationLogWriter getUserNotificationWriter();

	/**
	 * @return  �������� �� ����������� �������� ����������
	 */
	public abstract boolean isUserMessageLogging();

	/**
	 * @return  �������� �� ����������� ��������� �������� ����������
	 */
	public abstract boolean isUserNotificationLogging() ;

}
