package com.rssl.phizicgate.wsgate.services.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 03.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateInfoServiceTypesCorrelation
{
	public static final Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.utils.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.utils.generated.Code.class);
		types.put(com.rssl.phizic.gate.utils.InputMode.class, null);

		types.put(com.rssl.phizic.gate.dictionaries.billing.Billing.class, com.rssl.phizicgate.wsgate.services.utils.generated.Billing.class);
		types.put(com.rssl.phizgate.common.services.GateConfigurationImpl.class, com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration.class);
		types.put(com.rssl.phizic.gate.ConnectMode.class, null);
	}
}
