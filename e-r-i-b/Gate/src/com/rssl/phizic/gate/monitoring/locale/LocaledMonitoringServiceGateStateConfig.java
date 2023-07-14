package com.rssl.phizic.gate.monitoring.locale;

import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Локалезависимые настройки мониторинга статуса сервиса
 * @author komarov
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledMonitoringServiceGateStateConfig extends MonitoringServiceGateStateConfig
{
	private Set<MonitoringServiceGateStateConfigResources> resources;

	@Override
	public String getMessageText()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getMessageText();
		return super.getMessageText();
	}
}
