package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * селектор, проверяющий доступ сервиса
 */

class ServiceNameWaySelector<T> extends WaySelector<T>
{
	protected ExternalSystemIntegrationMode getMode(T source, String serviceName)
	{
		return getServiceMode(serviceName);
	}

	static ExternalSystemIntegrationMode getServiceMode(String serviceName)
	{
		return getConfig().getIntegrationMode(serviceName);
	}
}
