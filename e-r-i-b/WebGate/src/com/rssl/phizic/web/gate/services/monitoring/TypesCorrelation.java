package com.rssl.phizic.web.gate.services.monitoring;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

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
		tempTypes.put(com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringParameters.class, com.rssl.phizic.gate.monitoring.MonitoringParameters.class);
		tempTypes.put(com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringStateParameters.class, com.rssl.phizic.gate.monitoring.MonitoringStateParameters.class);
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
