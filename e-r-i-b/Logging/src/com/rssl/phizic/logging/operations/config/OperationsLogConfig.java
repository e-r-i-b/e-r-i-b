package com.rssl.phizic.logging.operations.config;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.system.LogLevelConfig;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Krenev
 * @ created 19.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class OperationsLogConfig extends LogLevelConfig
{
	public static final String LEVEL_NAME = "com.rssl.phizic.logging.operations.level";
	public static final String EXTENDED_LEVEL_NAME = "com.rssl.phizic.logging.operations.extended.level";
	public static final String MODE_NAME = "com.rssl.phizic.logging.operations.mode";
	public static final String EXTENDED_MODE_NAME = "com.rssl.phizic.logging.operations.extended.mode";
	public static final String MAIN_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.OperationLogWriter";
	public static final String BACKUP_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.OperationLogWriter.backup";

	/**
	 *  Уровень детализации.
	 */
	public enum Level
	{
		SHORT,// Кратко: только наименование выполненного пользователем действия
		MEDIUM,//Средняя: наименование + URL
		DETAILED//Подробно:наименование, URL + параметры операции.
	}

	/**
	 * Режим логирования
	 */
	public enum Mode
	{
		ACTIVE,// Активные операции
		FULL,// Все операции
		OFF // Выключен
	}

	protected OperationsLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return уровень детализации
	 */
	public abstract Level getLevel();

	/**
	 * @return режим логирования
	 */
	public abstract Mode getMode();

	/**
	 * @return уровень детализации расширенного логирования
	 */
	public abstract Level getExtendedLevel();

	/**
	 * @return режим расширенного логирования
	 */
	public abstract Mode getExtendedMode();

	/**
	 * @return основной writer
	 */
	public abstract LogWriter getMainWriter();

	/**
	 * @return резервный writer
	 */
	public abstract LogWriter getBackupWriter();
}
