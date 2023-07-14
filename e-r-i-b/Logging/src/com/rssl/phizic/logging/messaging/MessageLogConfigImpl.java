package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.logging.writers.StatisticLogWriter;
import com.rssl.phizic.logging.writers.UserMessageLogWriter;
import com.rssl.phizic.logging.writers.UserNotificationLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author eMakarov
 * @ created 26.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class MessageLogConfigImpl extends MessageLogConfig
{
	private MessageLogWriter mainWriter;
	private MessageLogWriter backupWriter;
	private StatisticLogWriter pushWriter;
	private UserMessageLogWriter userMessageWriter;
	private UserNotificationLogWriter userNotificationWriter;
	private boolean userMessageLogging;
	private boolean userNotificationLogging;
	private Map<System, Boolean> states = new HashMap<System, Boolean>();
	private Map<System, Boolean> extendedStates = new HashMap<System, Boolean>();
	//instancename для записи в БД, для DataBaseMessageLogWriter
	private String dbWriterInstanceName;
	private String jMSQueueName;
	private String jMSQueueFactoryName;
	private List<Pattern> excludedMessages;

	public MessageLogConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public boolean getLogState(System systemName)
	{
		if (systemName == null)
		{
			// если нас спрашивают писать или нет непонятно из какой системы, то обязательно пишем!!!
			return true;
		}
		Boolean state = states.get(systemName);
		if (state == null)
		{
			// если нас спрашивают писать или нет непонятно из какой системы, то обязательно пишем!!!
			return true;
		}
		return state;
	}

	public boolean getExtendedLogState(System systemName)
	{
		if (systemName == null)
		{
			// если нас спрашивают писать или нет непонятно из какой системы, то обязательно пишем!!!
			return true;
		}
		Boolean state = extendedStates.get(systemName);
		if (state == null)
		{
			// если нас спрашивают писать или нет непонятно из какой системы, то обязательно пишем!!!
			return true;
		}
		return state;
	}

	public MessageLogWriter getMainWriter()
	{
		return mainWriter;
	}

	public MessageLogWriter getBackupWriter()
	{

		return backupWriter;
	}

	public StatisticLogWriter getPushStatisticWriter()
	{
		return pushWriter;
	}

	public UserMessageLogWriter getUserMessageWriter()
	{
		return userMessageWriter;
	}

	public UserNotificationLogWriter getUserNotificationWriter()
	{
		return userNotificationWriter;
	}

	public boolean isUserMessageLogging()
	{
		return userMessageLogging;
	}

	public boolean isUserNotificationLogging()
	{
		return userNotificationLogging;
	}

	public List<Pattern> getExcludedMessages()
	{
		return excludedMessages;
	}

	/**
	 * Обновить содержимое конфига
	 */
	public void doRefresh() throws ConfigurationException
	{
		String className = getProperty(MessageLogConfig.MAIN_WRITER_CLASS_KEY);
		if (mainWriter == null || !mainWriter.getClass().getName().equals(className))
		{
			mainWriter = loadWriter(className);
		}
		className = getProperty(MessageLogConfig.BACKUP_WRITER_CLASS_KEY);
		if (backupWriter == null || !backupWriter.getClass().getName().equals(className))
		{
			backupWriter = loadWriter(className);
		}
		className = getProperty(MessageLogConfig.PUSH_WRITER_CLASS_KEY);
		if (pushWriter == null || !pushWriter.getClass().getName().equals(className))
		{
			pushWriter = loadWriter(className);
		}
		className = getProperty(MessageLogConfig.USER_MESSAGE_LOG_WRITER_CLASS_KEY);
		if (userMessageWriter == null || !userMessageWriter.getClass().getName().equals(className))
		{
			userMessageWriter = loadWriter(className);
		}
		userMessageLogging = getBoolProperty(MessageLogConfig.USER_MESSAGE_LOGGING_ON_KEY);
		className = getProperty(MessageLogConfig.USER_NOTIFICATION_LOG_WRITER_CLASS_KEY);
		if (userNotificationWriter == null || !userNotificationWriter.getClass().getName().equals(className))
		{
			userNotificationWriter = loadWriter(className);
		}
		userNotificationLogging = getBoolProperty(MessageLogConfig.USER_NOTIFICATION_LOGGING_ON_KEY);

		states = initLogLevels("");
		extendedStates = initLogLevels(EXTENDED_PREFIX);

		dbWriterInstanceName = getProperty(DB_INSTANCE_NAME);
		jMSQueueFactoryName = getProperty(JMS_QUEUE_FACTORY_NAME);
		jMSQueueName = getProperty(JMS_QUEUE_NAME);

		excludedMessages = initExcludedMessages();
	}

	private List<Pattern> initExcludedMessages()
	{
		Properties properties = getProperties(EXCLUDED_MESSAGES_PROPERTY_KEY);

		List<Pattern> result = new ArrayList<Pattern>();

		for (Map.Entry entry : properties.entrySet())
		{
			result.add(Pattern.compile((String) entry.getValue()));
		}

		return result;
	}

	private Map<System, Boolean> initLogLevels(String extendedPrefix)
	{
		Map<System, Boolean> result = new HashMap<System, Boolean>();
		Properties properties = getProperties(Constants.MESSAGE_LOG_PREFIX);
		String levelPrefix = Constants.MESSAGE_LOG_PREFIX + extendedPrefix + LEVEL_PREFIX;
		for (Map.Entry entry : properties.entrySet())
		{
			String key = (String) entry.getKey();
			if (key.startsWith(levelPrefix))
			{
				String value = (String) entry.getValue();
				key = key.substring(levelPrefix.length());

				result.put(System.valueOf(key), value.equals("on"));
			}
		}
		return result;
	}

	private <T> T loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}
		try
		{
			return (T) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}

	public String getDbWriterInstanceName()
	{
		return dbWriterInstanceName;
	}

	public String getJMSQueueName()
	{
		return jMSQueueName;
	}

	public String getJMSQueueFactoryName()
	{
		return jMSQueueFactoryName;
	}
}
