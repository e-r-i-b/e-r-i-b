package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author Omeliyanchuk
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class OfficeCacheKeyComposer extends AbstractCacheKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		int pos = getOneParam(params);
		if(args[pos]==null)
			return null;
		Office office = (Office)args[pos];
		return office.getSynchKey().toString();
	}

	public String getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Office))
			return null;
		Office office = (Office)result;
		return office.getSynchKey().toString();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
