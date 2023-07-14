package com.rssl.phizicgate.wsgate.services.fund;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 22.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Соотношение типов
 */
public class TypesCorrelation
{
	private static final Map<Class, Class> types = new HashMap<Class, Class>();

	/**
	 * @return мап соотношения типов
	 */
	public static Map<Class, Class> getTypes()
	{
		return types;
	}

	static
	{
		types.put(com.rssl.phizic.gate.fund.Request.class,                          com.rssl.phizicgate.wsgate.services.fund.generated.Request.class);
		types.put(com.rssl.phizic.gate.clients.GUID.class,                          com.rssl.phizicgate.wsgate.services.fund.generated.GUID.class);
		types.put(com.rssl.phizic.gate.fund.Response.class,                         com.rssl.phizicgate.wsgate.services.fund.generated.Response.class);
		types.put(com.rssl.phizic.gate.fund.FundInfo.class,                         com.rssl.phizicgate.wsgate.services.fund.generated.FundInfo.class);
		types.put(com.rssl.phizic.common.types.fund.FundRequestState.class,         null);
		types.put(com.rssl.phizic.common.types.fund.FundResponseState.class,        null);

		types.put(com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo.class,     com.rssl.phizicgate.wsgate.services.fund.impl.RequestInfoImpl.class);
	}
}
