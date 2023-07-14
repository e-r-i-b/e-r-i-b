package com.rssl.phizic.wsgate.currency;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class TypesCorrelation
{
	public static Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.wsgate.currency.generated.Currency.class);
	}	
}
