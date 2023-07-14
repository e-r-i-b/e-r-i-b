package com.rssl.phizic.logging.operations;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 * Writer с поддержкой резервной записи. Те если вслучае записи в лог происходит исключение,
 * производится попытка записи по резервному каналу.
 */

public class BackupSupportLogWriter implements LogWriter
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void writeActiveOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		try
		{
			getMainWriter().writeActiveOperation(reader, start, end);
		}
		catch (Exception e)
		{
			log.warn("Ошибка записи в журнал действий пользователя. Будет произведена попытка записи по резервному каналу", e);
			refresh();
			getBackupWriter().writeActiveOperation(reader, start, end);
		}
	}

	public void writePassiveOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		try
		{
			getMainWriter().writePassiveOperation(reader, start, end);
		}
		catch (Exception e)
		{
			log.warn("Ошибка записи в журнал действий пользователя. Будет произведена попытка записи по резервному каналу", e);
			refresh();
			getBackupWriter().writePassiveOperation(reader, start, end);
		}
	}

	public void writeSecurityOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		try
		{
			getMainWriter().writeSecurityOperation(reader, start, end);
		}
		catch (Exception e)
		{
			log.warn("Ошибка записи в журнал действий пользователя. Будет произведена попытка записи по резервному каналу", e);
			refresh();
			getBackupWriter().writeSecurityOperation(reader, start, end);
		}
	}

	private void refresh()
	{
	}

	private LogWriter getMainWriter()
	{
		return ConfigFactory.getConfig(OperationsLogConfig.class).getMainWriter();
	}

	/**
	 * @return резервный writer.
	 */
	private LogWriter getBackupWriter()
	{
		LogWriter writer = ConfigFactory.getConfig(OperationsLogConfig.class).getBackupWriter();
		if (writer == null)
		{
			throw new ConfigurationException("Не задан резервный канал записи журнала действий пользователя " + OperationsLogConfig.BACKUP_WRITER_CLASS_KEY);
		}
		return writer;
	}
}
