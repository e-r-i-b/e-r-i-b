package com.rssl.phizic.web.gate.services.util;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 03.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.web.gate.services.util.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);

		types.put(com.rssl.phizic.web.gate.services.util.generated.Billing.class, com.rssl.phizgate.billing.Billing.class);
		types.put(com.rssl.phizic.web.gate.services.util.generated.GateConfiguration.class, com.rssl.phizgate.common.services.GateConfigurationImpl.class);
		types.put(com.rssl.phizic.gate.ConnectMode.class, null);
	}
}
