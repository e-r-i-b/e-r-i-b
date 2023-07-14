package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @ author: filimonova
 * @ created: 06.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class PrintAccountInfoForm extends EditFormBase
{
	private AccountLink accountLink;
	private AccountAbstract accountAbstract;
	private boolean printAbstract;

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public AccountAbstract getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(AccountAbstract accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public boolean isPrintAbstract()
	{
		return printAbstract;
	}

	public void setPrintAbstract(boolean printAbstract)
	{
		this.printAbstract = printAbstract;
	}
}
