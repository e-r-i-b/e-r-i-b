package com.rssl.phizic.monitoring.services;

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

		tempTypes.put(com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateConfigImpl.class, com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig.class);
		tempTypes.put(com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl.class, com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig.class);
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
