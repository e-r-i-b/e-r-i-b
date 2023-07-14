package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author hudyakov
 * @ created 28.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class ActiveAccountFilter implements AccountFilter
{
	/**
	 * ѕроверка статуса вклада/счета на предмет его активности
	 * @param account  вклада/счет
	 * @return активен ли вклада/счет
	 * @throws TemporalBusinessException если вклад/счет €вл€етс€ mock
	 */
	public boolean accept(Account account) throws TemporalBusinessException
	{
		if (MockHelper.isMockObject(account))
			throw new TemporalBusinessException("ќшибка при получении информации по счету є" + StringHelper.getEmptyIfNull(account.getNumber()));

		return account.getAccountState() == AccountState.OPENED || account.getAccountState() == AccountState.LOST_PASSBOOK;
	}

	/**
	 * ѕроверка статуса вклада/счета на предмет его активности
	 * @param accountLink вклада/счет
	 * @return активен ли вклада/счет если вклад/счет €вл€етс€ mock возращаетс€ false
	 */
	public boolean accept(AccountLink accountLink)
	{
		try
		{
			return accept(accountLink.getAccount());
		}
		catch (TemporalBusinessException e)
		{
			return false;
		}
	}
}
