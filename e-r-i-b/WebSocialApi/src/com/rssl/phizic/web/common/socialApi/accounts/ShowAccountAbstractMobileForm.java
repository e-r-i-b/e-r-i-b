package com.rssl.phizic.web.common.socialApi.accounts;

import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.web.common.socialApi.common.FilterFormBase;

/**
 * Выписка по вкладу
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountAbstractMobileForm extends FilterFormBase
{
    //out
	private AccountAbstract accountAbstract;
	private boolean isError = false;

	public AccountAbstract getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(AccountAbstract accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}
}