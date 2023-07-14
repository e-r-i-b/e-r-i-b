package com.rssl.phizic.gate.cache.proxy.composers;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class NullCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static final String EMPTY_KEY = "emptykey";

	public Serializable getKey(Object[] args, String params)
	{
		return EMPTY_KEY;
	}
}
