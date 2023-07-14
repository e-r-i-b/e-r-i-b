package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 02.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class LoanCacheKeyComposer extends AbstractCacheKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}

		return ((Loan)args[paramNum]).getAccountNumber();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Loan))
			return null;

		Loan loan = (Loan)result;
		return loan.getAccountNumber();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
