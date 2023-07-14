package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.DepositConfig;

import java.util.List;

/**
 *
 * Фильтр отсеивает вклады закрыть которые нельзя. 
 *
 * @author Balovtsev
 * @created 07.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingFilter extends ActiveAccountFilter
{
	public AccountClosingFilter()
	{
	}

	public boolean accept(Account account) throws TemporalBusinessException
	{
		return super.accept(account) && acceptSubtypes(account) && !isAccountArrested(account);
	}

	private boolean isAccountArrested(Account account)
	{
		return AccountState.ARRESTED == account.getAccountState();
	}

	private boolean acceptSubtypes(Account account)
	{
		List<Long> forbiddenKinds;
		forbiddenKinds = ConfigFactory.getConfig(DepositConfig.class).getAccountsKindsForbiddenClosing();

		for(Long kind : forbiddenKinds)
		{
			if(account.getKind() != null && account.getKind().equals(kind))
			{
				return false;
			}
		}
		return true;
	}
}
