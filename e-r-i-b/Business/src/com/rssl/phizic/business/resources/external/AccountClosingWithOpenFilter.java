package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;

/**
 * Фильтр отсеивает вклады с нулевым остатком и вклады, которые закрыть нельзя
 * @author Barinov
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingWithOpenFilter extends AccountClosingFilter
{
	public boolean accept(Account account) throws TemporalBusinessException
	{
		Money balance = account.getBalance();
		if(balance == null)
			return false;
		return balance.getAsCents()>0 && super.accept(account);
	}
}
