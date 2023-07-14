package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.ima.IMAccount;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * @Author: Balovtsev
 * @Created: 09.08.2010
 * @$Revision$
 */
public class IMAccountIDCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(result != null && result instanceof IMAccount)
		{
			return ((IMAccount)result).getId();
		}
		return null;
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
