package com.rssl.phizic.logging.operations.config;

import com.rssl.phizic.config.*;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Krenev
 * @ created 20.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class OperationsLogConfigImpl extends OperationsLogConfig
{
	private Level level;
	private Mode mode;
	private Level extendedLevel;
	private Mode extendedMode;
	private LogWriter mainWriter;
	private LogWriter backupWriter;

	public OperationsLogConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public Level getLevel()
	{
		return level;
	}

	public Mode getMode()
	{
		return mode;
	}

	public Level getExtendedLevel()
	{
		return extendedLevel;
	}

	public Mode getExtendedMode()
	{
		return extendedMode;
	}

	public LogWriter getMainWriter()
	{
		return mainWriter;
	}

	public LogWriter getBackupWriter()
	{

		return backupWriter;
	}

	public void doRefresh() throws ConfigurationException
	{
		level = Level.valueOf(getProperty(OperationsLogConfig.LEVEL_NAME));
		mode = Mode.valueOf(getProperty(OperationsLogConfig.MODE_NAME));

		String extendedLevelValue = getProperty(OperationsLogConfig.EXTENDED_LEVEL_NAME);
		if (StringHelper.isNotEmpty(extendedLevelValue))
			extendedLevel = Level.valueOf(extendedLevelValue);
		else
			extendedLevel = Level.DETAILED;

		String extendedModeValue = getProperty(OperationsLogConfig.EXTENDED_MODE_NAME);
		if (StringHelper.isNotEmpty(extendedModeValue))
			extendedMode = Mode.valueOf(extendedModeValue);
		else
			extendedMode = Mode.FULL;

		String className = getProperty(OperationsLogConfig.MAIN_WRITER_CLASS_KEY);
		if (mainWriter == null || !mainWriter.getClass().getName().equals(className))
		{
			mainWriter = loadWriter(className);
		}
		className = getProperty(OperationsLogConfig.BACKUP_WRITER_CLASS_KEY);
		if (backupWriter == null || !backupWriter.getClass().getName().equals(className))
		{
			backupWriter = loadWriter(className);
		}
	}

	private LogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}
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
