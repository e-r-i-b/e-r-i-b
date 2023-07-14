package com.rssl.phizicgate.wsgate.services.offices;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 05.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizicgate.wsgate.services.offices.generated.Office.class, com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.offices.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.offices.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.offices.generated.Code.class);
	}
}
