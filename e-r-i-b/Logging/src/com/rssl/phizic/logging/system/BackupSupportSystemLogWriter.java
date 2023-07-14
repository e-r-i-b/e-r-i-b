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
 * Writer � ���������� ��������� ������. �� ���� ������� ������ � ��� ���������� ����������,
 * ������������ ������� ������ �� ���������� ������.
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
			log.warn("������ ������ � ��������� ������. ����� ����������� ������� ������ �� ���������� ������", e);
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
			log.warn("������ �������� ������ ���������� �������. ����� ����������� ������� �������� ������ �� ���������� ������", e);
			refresh();
			return  getBackupWriter().createEntry(source, message, level);
		}
	}

	private void refresh()
	{
	}

	/**
	 * @return ��������� writer.
	 */
	private SystemLogWriter getBackupWriter()
	{
		SystemLogWriter writer = ConfigFactory.getConfig(SystemLogConfig.class).getBackupWriter();
		if (writer == null)
		{
			throw new ConfigurationException("�� ����� ��������� ����� ������ ���������� ������� " + SystemLogConfig.BACKUP_WRITER_CLASS_KEY);
		}
		return writer;
	}

	private SystemLogWriter getMainWriter()
	{
		return ConfigFactory.getConfig(SystemLogConfig.class).getMainWriter();
	}
}
