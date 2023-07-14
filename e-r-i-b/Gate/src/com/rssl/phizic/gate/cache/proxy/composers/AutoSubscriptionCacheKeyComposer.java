package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;

import java.io.Serializable;

/**
 * Композер для вычисления ключа по сущности автоподписки
 * @author niculichev
 * @ created 27.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return ((AutoSubscription) args[paramNum]).getExternalId();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof AutoSubscription))
			return null;

		AutoSubscription autoSub = (AutoSubscription) result;
		return autoSub.getExternalId();
	}
}
