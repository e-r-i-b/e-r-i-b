package com.rssl.phizic.gate.cache.proxy.composers;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 02.08.2010
 * @ $Author$
 * @ $Revision$
 */
// на входе два числа Long и кредит
public class LoanScheduleCacheKeyComposer extends LoanCacheKeyComposer
{
	private static final String SEPARATOR = "|";
	public String getKey(Object[] args, String params)
	{
		StringBuilder builder= new StringBuilder();
		builder.append(super.getKey(args, params));
		builder.append(SEPARATOR);
		if(args[0]!=null)
		{
			Long startNumber = (Long) args[0];
			builder.append(startNumber);
		}
		builder.append(SEPARATOR);
		if(args[1]!=null)
		{
			Long count = (Long) args[0];
			builder.append(count);
		}
		return builder.toString();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		return null;
	}

	public boolean isKeyFromResultSupported()
	{
		return false;
	}
}
