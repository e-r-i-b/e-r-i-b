package com.rssl.phizicgate.wsgateclient.services.monitoring;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
		tempTypes.put(com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateConfigImpl.class, com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig.class);
		tempTypes.put(com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl.class, com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig.class);
		types = Collections.unmodifiableMap(tempTypes);
	}

	public static Map<Class, Class> getTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return types;
	}
}
