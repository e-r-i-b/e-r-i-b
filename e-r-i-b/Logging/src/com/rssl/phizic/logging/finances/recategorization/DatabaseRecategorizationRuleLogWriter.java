package com.rssl.phizic.logging.finances.recategorization;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogConfig;
import com.rssl.phizic.logging.writers.RecategorizationRuleLogWriter;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.StatelessSession;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 * Writer для логирования в БД записей о добавлении/применении правила перекатегоризации
 */
public class DatabaseRecategorizationRuleLogWriter implements RecategorizationRuleLogWriter
{
	private String getInstanceName()
	{
		MessageLogConfig messageLogConfig = ConfigFactory.getConfig(MessageLogConfig.class);
		String instanceName = messageLogConfig.getDbWriterInstanceName();
		//если в настройках log.properties явно не задано в какую базу писать - пишем по умолчанию в Log
		if(StringHelper.isEmpty(instanceName))
			return Constants.DB_INSTANCE_NAME;
		return instanceName;
	}

	public void write(final ALFRecategorizationRuleEntry logEntry) throws Exception
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
