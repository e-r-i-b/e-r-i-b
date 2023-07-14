package com.rssl.phizic.web.client.ext.sevb.accounts;

import com.rssl.phizic.web.common.client.abstr.PrintAccountAbstractForm;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 03.08.2006
 * Time: 9:41:12
 */
public class PrintAccountAbstractExtendedForm extends PrintAccountAbstractForm
{
	Boolean copying = false;
	private List<Account> accounts;

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
