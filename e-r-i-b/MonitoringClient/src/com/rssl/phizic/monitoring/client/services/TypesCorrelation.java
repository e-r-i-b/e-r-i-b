package com.rssl.phizic.monitoring.client.services;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class TypesCorrelation
{
	private static final Map<Class, Class> types;

	static
	{
		Map<Class, Class> tempTypes = new HashMap<Class,Class>();

		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig.class, com.rssl.phizic.monitoring.client.services.generated.MonitoringServiceGateConfigImpl.class);
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig.class, com.rssl.phizic.monitoring.client.services.generated.MonitoringServiceGateStateConfigImpl.class);
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringGateState.class, null);
		tempTypes.put(com.rssl.phizic.gate.monitoring.InactiveType.class,        null);
		types = Collections.unmodifiableMap(tempTypes);
	}

	/**
	 * @return таблица коррел€ции
	 */
	public static Map<Class, Class> getTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return types;
	}
}
