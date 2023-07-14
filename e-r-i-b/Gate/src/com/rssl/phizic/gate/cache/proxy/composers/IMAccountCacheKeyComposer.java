package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.ima.IMAccount;

import java.io.Serializable;

/**
 * @Author: Balovtsev
 * @Created: 09.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountCacheKeyComposer extends IMAccountIDCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);

		IMAccount imAccount = (IMAccount) args[paramNum];
		return imAccount == null ? null : imAccount.getId();
	}
}
