package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.depo.DepoAccount;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return ((DepoAccount)args[paramNum]).getId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof DepoAccount))
			return null;

		DepoAccount depoAccount = (DepoAccount) result;
		return depoAccount.getId();
	}
}
