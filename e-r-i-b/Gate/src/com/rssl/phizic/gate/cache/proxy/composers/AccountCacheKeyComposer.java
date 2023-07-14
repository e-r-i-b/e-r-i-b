package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class AccountCacheKeyComposer extends AbstractCacheKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}

		return ((Account)args[paramNum]).getNumber();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Account))
			return null;

		Account account = (Account)result;
		return account.getNumber();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
