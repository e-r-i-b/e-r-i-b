package com.rssl.phizic.gate.config.monitoring;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Параметры для доступа к приложению мониторинга
 */

public abstract class MonitoringServicesConfigurationMBean extends Config
{
	protected MonitoringServicesConfigurationMBean(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return урл, на котором развернуто приложение мониторинга
	 */
	public abstract String getMonitoringAppUrl();
}
