package com.rssl.phizicgate.wsgateclient.services.offices;

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
	public static Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizgate.common.services.types.OfficeImpl.class, com.rssl.phizicgate.wsgateclient.services.offices.generated.Office.class);
		types.put(com.rssl.phizgate.common.services.types.CodeImpl.class,   com.rssl.phizicgate.wsgateclient.services.offices.generated.Code.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.offices.generated.Office.class, com.rssl.phizicgate.wsgateclient.services.offices.generated.Office.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.offices.generated.Code.class,   com.rssl.phizgate.common.services.types.CodeImpl.class);
	}
}
