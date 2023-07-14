package com.rssl.phizic.wsgate.dictionary;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author khudyakov
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	private static final Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizic.wsgate.recipients.generated.ResidentBank.class);

		types.put(com.rssl.phizic.wsgate.recipients.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
	}

	/**
	 * @return тип коррел€ции
	 */
	public static Map<Class, Class> getTypes()
	{
		return Collections.unmodifiableMap(types);
	}
}
