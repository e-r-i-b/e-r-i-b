package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.depo.DepoAccount;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountIdCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof DepoAccount))
			return null;

		DepoAccount depo = (DepoAccount) result;
		return depo.getId();
	}
}
