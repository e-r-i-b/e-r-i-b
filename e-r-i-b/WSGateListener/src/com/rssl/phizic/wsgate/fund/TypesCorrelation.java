package com.rssl.phizic.wsgate.fund;

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
	 * @return мап соотновшения типов
	 */
	public static Map<Class, Class> getTypes()
	{
		return types;
	}

	static
	{
		types.put(com.rssl.phizic.gate.fund.RequestInfo.class,                      com.rssl.phizic.wsgate.fund.generated.RequestInfo.class);
		types.put(com.rssl.phizic.common.types.fund.FundRequestState.class,         null);

		types.put(com.rssl.phizic.wsgate.fund.generated.Request.class,              com.rssl.phizic.wsgate.fund.types.RequestImpl.class);
		types.put(com.rssl.phizic.wsgate.fund.generated.Response.class,             com.rssl.phizic.wsgate.fund.types.ResponseImpl.class);
		types.put(com.rssl.phizic.wsgate.fund.generated.GUID.class,                 com.rssl.phizic.wsgate.fund.types.GUIDImpl.class);
		types.put(com.rssl.phizic.wsgate.fund.generated.FundInfo.class,             com.rssl.phizic.wsgate.fund.types.FundInfoImpl.class);
	}
}
