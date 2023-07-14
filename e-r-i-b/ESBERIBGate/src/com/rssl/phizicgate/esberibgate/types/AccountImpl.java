package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizgate.common.routable.AccountBase;
import com.rssl.phizic.gate.bankroll.Account;

/**
 * @ author: filimonova
 * @ created: 15.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountImpl extends AccountBase
{

	public AccountImpl(){};

	public AccountImpl(Account account)
	{
		this.id = account.getId();
		this.description = account.getDescription();
		this.type = account.getType();
		this.number = account.getNumber();
		this.currency = account.getCurrency();
		this.openDate = account.getOpenDate();
		this.creditAllowed = account.getCreditAllowed();
		this.debitAllowed = account.getDebitAllowed();
		this.interestRate = account.getInterestRate();
		this.balance = account.getBalance();
		this.maxSumWrite = account.getMaxSumWrite();
		this.accountState = account.getAccountState();
		this.office = account.getOffice();
		this.kind = account.getKind();
		this.subKind = account.getSubKind();
		this.minimumBalance = account.getMinimumBalance();
		this.demand = account.getDemand();
		this.passbook = account.getPassbook();
		this.prolongationDate = account.getProlongationDate();
		this.clientKind = account.getClientKind();
	}

	public Boolean getDebitAllowed()
	{
		if(debitAllowed != null)
			return debitAllowed;
		return true;
	}

	public Boolean getCreditAllowed()
	{
		if(creditAllowed != null)
			return creditAllowed;
		return true;
	}
}
