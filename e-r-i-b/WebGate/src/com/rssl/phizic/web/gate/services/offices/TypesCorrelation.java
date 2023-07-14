package com.rssl.phizic.web.gate.services.offices;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 04.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.web.gate.services.offices.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.offices.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.services.offices.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.offices.generated.Code.class);
	}
}
