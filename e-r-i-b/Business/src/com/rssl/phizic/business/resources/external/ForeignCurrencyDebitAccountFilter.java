package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.utils.MockHelper;

import java.math.BigDecimal;

/**
 * @author Gainanov
 * @ created 04.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class ForeignCurrencyDebitAccountFilter extends ActiveForeignCurrencyAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		if(!MockHelper.isMockObject(accountLink.getAccount()))
		{
			Boolean isDebitAllowed = accountLink.getAccount().getDebitAllowed();
			if ((isDebitAllowed == null || !isDebitAllowed) && accountLink.getAccount().getMaxSumWrite()!=null)
				if(BigDecimal.valueOf(0L).compareTo(accountLink.getAccount().getMaxSumWrite().getDecimal())<0)
					isDebitAllowed = true;
			return super.accept(accountLink) && (isDebitAllowed == null ? false : isDebitAllowed);
		}
		else return false;
	}
}
