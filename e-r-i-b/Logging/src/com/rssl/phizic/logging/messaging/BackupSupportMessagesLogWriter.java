package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;

/**
 * @author krenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 * Writer с поддержкой резервной записи. “е если вслучае записи в лог происходит исключение,
 * производитс€ попытка записи по резервному каналу.
 */

public class BackupSupportMessagesLogWriter implements MessageLogWriter
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private void refresh()
	{
	}

	public void write(MessagingLogEntry entry) throws Exception
	{
		try
		{
			getMainWriter().write(entry);
		}
		catch (Exception e)
		{
			log.warn("ќшибка записи в журнал сообщений. Ѕудет произведена попытка записи по резервному каналу.", e);
			refresh();
			getBackupWriter().write(entry);
		}
	}

	private MessageLogWriter getMainWriter()
	{
		return ConfigFactory.getConfig(MessageLogConfig.class).getMainWriter();
	}

	/**
	 * @return резервный writer.
	 */
	private MessageLogWriter getBackupWriter()
	{
		MessageLogWriter writer = ConfigFactory.getConfig(MessageLogConfig.class).getBackupWriter();
		if (writer == null)
		{
			throw new ConfigurationException("Ќе задан резервный канал записи журнала сообщений " + MessageLogConfig.BACKUP_WRITER_CLASS_KEY);
		}
		return writer;
	}
}
