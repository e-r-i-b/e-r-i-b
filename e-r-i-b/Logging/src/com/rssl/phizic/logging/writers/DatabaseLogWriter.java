package com.rssl.phizic.logging.writers;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogEntry;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.system.SystemLogConfig;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.StatelessSession;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Писатель логов напрямую в БД
 */
public class DatabaseLogWriter implements LogWriter
{

	private String getInstanceName()
	{
		SystemLogConfig systemLogConfig = ConfigFactory.getConfig(SystemLogConfig.class);
		String instanceName = systemLogConfig.getDbWriterInstanceName();
		//если в настройках log.properties явно не задано в какую базу писать - пишем по умолчанию в Log
		if(StringHelper.isEmpty(instanceName))
			return Constants.DB_INSTANCE_NAME;
		return instanceName;
	}

	public void write(final LogEntry logEntry) throws LoggingException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateActionStateless<Void>()
			{
				public Void run(StatelessSession session) throws Exception
				{
					session.insert(logEntry);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new LoggingException("Не удалось произвести запись в лог через DatabaseLogWriter",e);
		}
	}

}
