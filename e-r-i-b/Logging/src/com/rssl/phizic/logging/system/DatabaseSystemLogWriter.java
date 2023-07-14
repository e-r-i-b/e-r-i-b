package com.rssl.phizic.logging.system;

import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.StatelessSession;

/**
 * @author eMakarov
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class DatabaseSystemLogWriter extends SystemLogWriterBase
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

	protected void doWrite(final SystemLogEntry logEntry) throws Exception
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

	public SystemLogEntry createEntry(LogModule source, String message, LogLevel level)
	{
		return null;
	}
}
