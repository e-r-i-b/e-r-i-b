package com.rssl.phizicgate.wsgate.services.monitoring;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class TypesCorrelation
{
	private static final Map<Class, Class> types;

	static
	{
		Map<Class, Class> tempTypes = new HashMap<Class,Class>();
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringParameters.class, com.rssl.phizicgate.wsgate.services.monitoring.generated.MonitoringParameters.class);
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringStateParameters.class, com.rssl.phizicgate.wsgate.services.monitoring.generated.MonitoringStateParameters.class);
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringGateState.class, null);
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
