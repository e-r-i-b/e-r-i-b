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
 * ��������� ��� ������� � ���������� �����������
 */

public abstract class MonitoringServicesConfigurationMBean extends Config
{
	protected MonitoringServicesConfigurationMBean(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ���, �� ������� ���������� ���������� �����������
	 */
	public abstract String getMonitoringAppUrl();
}
