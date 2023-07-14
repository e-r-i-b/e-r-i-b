package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;

import java.io.Serializable;

/**
 * Композер ключа для кэша на метод полоучения автоподписки по внешнему идентикатору
 * @author Niculichev
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionExternalIdCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		Object res = args[getOneParam(params)];
		if(res instanceof AutoSubscription)
		{
			return ((AutoSubscription) res).getExternalId();
		}
		else
		{
			return (Serializable) res;
		}
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof AutoSubscription))
			return null;

		AutoSubscription autoSub = (AutoSubscription) result;
		return autoSub.getExternalId();
	}
}
