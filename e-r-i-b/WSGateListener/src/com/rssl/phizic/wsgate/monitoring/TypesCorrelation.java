package com.rssl.phizic.wsgate.monitoring;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class TypesCorrelation
{
	private static final Map<Class, Class> types;

	static
	{
		Map<Class, Class> tempTypes = new HashMap<Class,Class>();

		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig.class, com.rssl.phizic.wsgate.monitoring.generated.MonitoringServiceGateConfigImpl.class);
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig.class, com.rssl.phizic.wsgate.monitoring.generated.MonitoringServiceGateStateConfigImpl.class);
		tempTypes.put(com.rssl.phizic.gate.monitoring.MonitoringGateState.class, null);
		tempTypes.put(com.rssl.phizic.gate.monitoring.InactiveType.class,        null);
		types = Collections.unmodifiableMap(tempTypes);
	}

	public static Map<Class, Class> getTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return types;
	}
}
