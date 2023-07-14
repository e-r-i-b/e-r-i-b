package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * User: Novikov_A
 * Date: 25.12.2006
 * Time: 18:05:06
 */
public class PrintAccountInfoForm extends EditFormBase
{
	private Account account;
	private String fullNameClient;
	private Long accountId;

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public String getFullNameClient()
	{
		return fullNameClient;
	}

	public void setFullNameClient(String fullNameClient)
	{
		this.fullNameClient = fullNameClient;
	}

	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}
}
