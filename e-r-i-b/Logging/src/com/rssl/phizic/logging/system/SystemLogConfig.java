package com.rssl.phizic.logging.system;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.SystemLogWriter;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author eMakarov
 * @ created 18.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class SystemLogConfig extends LogLevelConfig
{
	String LOG_PREFIX = Constants.SYSTEM_LOG_PREFIX;
	public static final String MAIN_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.SystemLogWriter";
	public static final String BACKUP_WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.SystemLogWriter.backup";
	public static final String DB_INSTANCE_NAME = "com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName";
	public static final String JMS_QUEUE_NAME = "com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName";
	public static final String JMS_QUEUE_FACTORY_NAME = "com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName";

	protected SystemLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ���������� ������� ����������� ��� ������
	 * @param moduleName - �������� ������
	 * @return ������� �����������
	 */
	public abstract int getModuleLogLevel(LogModule moduleName);

	/**
	 * ���������� ������� ������������ ����������� ��� ������
	 * @param moduleName - �������� ������
	 * @return ������� ������������ �����������
	 */
	public abstract int getModuleExtendedLogLevel(LogModule moduleName);

	/**
	 * @return �������� writer
	 */
	public abstract SystemLogWriter getMainWriter();

	/**
	 * @return ��������� writer
	 */
	public abstract SystemLogWriter getBackupWriter();

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
}
