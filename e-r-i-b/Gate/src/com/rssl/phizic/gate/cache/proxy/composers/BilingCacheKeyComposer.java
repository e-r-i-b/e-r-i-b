package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.dictionaries.billing.Billing;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 13.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class BilingCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		if(args[paramNum]!=null && args[paramNum] instanceof Billing)
		{			
			Billing billing = (Billing)args[paramNum];
			return billing.getSynchKey().toString();
		}
		else return null;
	}
}
