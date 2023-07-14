package com.rssl.phizic.wsgate.offices;

import java.util.HashMap;
import java.util.Map;

/**
 * @author niculichev
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public final static Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizic.wsgate.offices.generated.Office.class,    com.rssl.phizic.wsgate.types.OfficeImpl.class);
		types.put(com.rssl.phizic.wsgate.offices.generated.Code.class,      com.rssl.phizic.wsgate.types.CodeImpl.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class,  com.rssl.phizic.wsgate.offices.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class,    com.rssl.phizic.wsgate.offices.generated.Code.class);
	}
}
