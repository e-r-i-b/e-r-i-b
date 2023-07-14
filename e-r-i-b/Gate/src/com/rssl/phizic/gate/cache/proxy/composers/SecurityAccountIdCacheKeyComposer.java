package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.security.SecurityAccount;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 21.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SecurityAccountIdCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof SecurityAccount))
			return null;

		SecurityAccount securityAccount = (SecurityAccount)result;
		return securityAccount.getId();
	}
}
