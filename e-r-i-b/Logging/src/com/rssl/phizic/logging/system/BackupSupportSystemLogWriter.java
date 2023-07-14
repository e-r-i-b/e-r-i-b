package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.writers.SystemLogWriter;
import org.apache.commons.logging.LogFactory;

/**
 * @author krenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 * Writer с поддержкой резервной записи. Те если вслучае записи в лог происходит исключение,
 * производится попытка записи по резервному каналу.
 */

public class BackupSupportSystemLogWriter implements SystemLogWriter
{
	private org.apache.commons.logging.Log log = LogFactory.getLog(BackupSupportSystemLogWriter.class);

	public void write(LogModule source, String message, LogLevel level) throws Exception
	{
		try
		{
			getMainWriter().write(source, message, level);
		}
		catch (Exception e)
		{
			log.warn("Ошибка записи в системный журнал. Будет произведена попытка записи по резервному каналу", e);
			refresh();
			getBackupWriter().write(source, message, level);
		}
	}

	public SystemLogEntry createEntry(LogModule source, String message, LogLevel level)
	{
		try
		{
			return getMainWriter().createEntry(source, message, level);
		}
		catch (Exception e)
		{
			log.warn("Ошибка создания записи системного журнала. Будет произведена попытка создания записи по резервному каналу", e);
			refresh();
			return  getBackupWriter().createEntry(source, message, level);
		}
	}

	private void refresh()
	{
	}

	/**
	 * @return резервный writer.
	 */
	private SystemLogWriter getBackupWriter()
	{
		SystemLogWriter writer = ConfigFactory.getConfig(SystemLogConfig.class).getBackupWriter();
		if (writer == null)
		{
			throw new ConfigurationException("Не задан резервный канал записи системного журнала " + SystemLogConfig.BACKUP_WRITER_CLASS_KEY);
		}
		return writer;
	}

	private SystemLogWriter getMainWriter()
	{
		return ConfigFactory.getConfig(SystemLogConfig.class).getMainWriter();
	}
}
