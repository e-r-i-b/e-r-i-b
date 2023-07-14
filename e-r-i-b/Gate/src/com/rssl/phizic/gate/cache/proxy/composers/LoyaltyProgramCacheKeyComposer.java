package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.loyalty.LoyaltyProgram;

import java.io.Serializable;

/**
 * @author gladishev
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramCacheKeyComposer extends LoyaltyProgramIdCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return ((LoyaltyProgram) args[paramNum]).getExternalId();
	}
}