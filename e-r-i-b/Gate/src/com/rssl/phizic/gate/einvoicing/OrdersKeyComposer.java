package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.cache.proxy.composers.AbstractCacheKeyComposer;

import java.io.Serializable;

/**
 * @author bogdanov
 * @ created 26.02.14
 * @ $Author$
 * @ $Revision$
 */

public class OrdersKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		return (Serializable)args[0];
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (result instanceof ShopOrder)
			return ((ShopOrder) result).getUuid();
		return (Serializable) params[0];
	}
}
