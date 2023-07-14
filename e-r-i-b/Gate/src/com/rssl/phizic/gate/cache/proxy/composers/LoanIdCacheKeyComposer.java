package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.loans.Loan;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 01.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class LoanIdCacheKeyComposer extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Loan))
			return null;

		Loan loan = (Loan)result;
		return loan.getId();
	}
}
