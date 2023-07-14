package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.loyalty.LoyaltyProgram;

import java.io.Serializable;

/**
 * @author gladishev
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramIdCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof LoyaltyProgram))
			return null;

		LoyaltyProgram program = (LoyaltyProgram)result;
		return program.getExternalId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}