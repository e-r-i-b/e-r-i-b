package com.rssl.phizic.logging.csaAction;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.CSAActionLogWriter;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.StatelessSession;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Writer записей в журнал входов ЦСА в БД.
 */
public class DBCSAActionLogWriter implements CSAActionLogWriter
{
	private String getInstanceName()
	{
		CSAActionLogConfig actionLogConfig = ConfigFactory.getConfig(CSAActionLogConfig.class);
		String instanceName = actionLogConfig.getDbWriterInstanceName();
		//если в настройках log.properties явно не задано в какую базу писать - пишем по умолчанию в Log
		if(StringHelper.isEmpty(instanceName))
			return Constants.DB_INSTANCE_NAME;
		return instanceName;
	}

	public void write(final CSAActionLogEntryBase logEntry) throws Exception
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
}
