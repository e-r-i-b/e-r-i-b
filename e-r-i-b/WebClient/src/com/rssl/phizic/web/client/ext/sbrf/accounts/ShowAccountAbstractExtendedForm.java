package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.web.client.abstr.ShowAccountAbstractForm;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.common.types.DateSpan;

import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountAbstractExtendedForm extends ShowAccountAbstractForm
{
	Boolean copying = false;
	Map<Account,DateSpan> periodToClose;
	private List<Account> accounts;

	public Map<Account, DateSpan> getPeriodToClose()
	{
		return periodToClose;
	}

	public void setPeriodToClose(Map<Account, DateSpan> periodToClose)
	{
		this.periodToClose = periodToClose;
	}

	public Boolean getCopying()
	{
		return copying;
	}

	public void setCopying(Boolean copying)
	{
		this.copying = copying;
	}

	public List<Account> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<Account> accounts)
	{
		this.accounts = accounts;
	}
}
