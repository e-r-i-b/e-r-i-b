package com.rssl.phizic.logging.writers;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг для получения писателей сообщений для логов.
 */
public class LogWritersConfig extends Config
{
	private static final String CONTACT_SYNC_COUNT_EXCEED_LOG_WRITER_KEY = "com.rssl.phizic.logging.contact.synchronization.ContactSyncCountExceedLog.writer";

	private LogWriter contactSyncCountExceedLogWriter;

	/**
	 * Конструктор
	 * @param reader читатель настроек
	 */
	public LogWritersConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return писатель лога для сообщений о превышении количества синхронизации адрессной книги
	 */
	public LogWriter getContactSyncCountExceedLogWriter()
	{
		return contactSyncCountExceedLogWriter;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		contactSyncCountExceedLogWriter = getLogWriterByKey(CONTACT_SYNC_COUNT_EXCEED_LOG_WRITER_KEY);
	}

	private LogWriter getLogWriterByKey(String key)
	{
		String className = getProperty(key);
		if(StringHelper.isEmpty(className))
			throw new ConfigurationException("Не задан писатель лога для ключа " + key);
		try
		{
			return (LogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}
}
