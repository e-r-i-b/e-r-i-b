package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.clients.Client;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClientIDCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Client))
			return null;

		Client client = (Client)result;
		return client.getId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}