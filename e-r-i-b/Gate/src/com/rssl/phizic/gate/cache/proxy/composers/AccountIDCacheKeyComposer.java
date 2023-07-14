package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.bankroll.Account;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class AccountIDCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Account))
			return null;

		Account account = (Account)result;
		return account.getId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
