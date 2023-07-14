package com.rssl.phizicgate.wsgateclient.services.statistics.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 01.11.2014
 * @ $Author$
 * @ $Revision$
 */

public class TypesCorrelation
{
	private static final Map<Class, Class> types;

	static
	{
		Map<Class, Class> mapping = new HashMap<Class, Class>();
		mapping.put(com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo.class,
				com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo.class);
		mapping.put(com.rssl.phizic.common.types.Application.class, null);
		mapping.put(com.rssl.phizic.logging.messaging.System.class, null);
		types = Collections.unmodifiableMap(mapping);
	}

	/**
	 * @return тип коррел€ции
	 */
	public static Map<Class, Class> getTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return types;
	}
}
