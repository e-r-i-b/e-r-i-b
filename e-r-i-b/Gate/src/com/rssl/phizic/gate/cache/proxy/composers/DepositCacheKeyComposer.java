package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepositCacheKeyComposer  extends AbstractCacheKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}

		return ((Deposit)args[paramNum]).getId();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Deposit))
			return null;

		Deposit deposit = (Deposit)result;
		return deposit.getId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
