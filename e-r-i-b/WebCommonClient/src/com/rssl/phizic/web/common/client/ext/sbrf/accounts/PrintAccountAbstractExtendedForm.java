package com.rssl.phizic.web.common.client.ext.sbrf.accounts;

import com.rssl.phizic.web.common.client.abstr.PrintAccountAbstractForm;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.List;
import java.util.Map;

/**
 * User: Omeliyanchuk
 * Date: 03.08.2006
 * Time: 9:41:12
 */
public class PrintAccountAbstractExtendedForm extends PrintAccountAbstractForm
{
	private Boolean               copying = false;
	private List<Account>         accounts;
	private Map<Account,DateSpan> periodToClose;

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
