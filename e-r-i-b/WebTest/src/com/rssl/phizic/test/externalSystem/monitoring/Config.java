package com.rssl.phizic.test.externalSystem.monitoring;

import com.rssl.phizic.utils.PropertyHelper;
import org.apache.commons.lang.BooleanUtils;

import java.util.Properties;

/**
 * @author akrenev
 * @ created 26.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг мониторинга
 */

class Config
{
	private static final Config instance = new Config();

	private final boolean useMonitoring;
	private final int threadCount;

	private Config()
	{
		Properties properties = PropertyHelper.readProperties("/com/rssl/phizic/test/externalSystem/monitoring/monitoringESSettings.properties");
		useMonitoring = BooleanUtils.toBoolean(properties.getProperty("monitoring.state"));
		threadCount = new Long(properties.getProperty("monitoring.threads")).intValue();
	}

	static Config getInstance()
	{
		return instance;
	}

	boolean useMonitoring()
	{
		return useMonitoring;
	}

	int getThreadCount()
	{
		return threadCount;
	}
}
