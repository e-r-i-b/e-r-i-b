package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClientCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}

		return ((Client)args[paramNum]).getId();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(result==null || !(result instanceof Client) )
			return null;

		Client client = (Client)result;
		return client.getId();
	}
}
