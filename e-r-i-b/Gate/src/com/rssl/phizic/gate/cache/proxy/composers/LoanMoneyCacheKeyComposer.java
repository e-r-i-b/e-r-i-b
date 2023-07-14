package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 02.08.2010
 * @ $Author$
 * @ $Revision$
 */
// на входе кредит и сумма
public class LoanMoneyCacheKeyComposer extends LoanCacheKeyComposer
{
	private static final String SEPARATOR = "|";
	public String getKey(Object[] args, String params)
	{
		StringBuilder builder= new StringBuilder();
		builder.append(super.getKey(args, params));
		builder.append(SEPARATOR);
		if(args[1]!=null)
		{
			Money amount = (Money) args[1];
			builder.append(amount.getAsCents());
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
