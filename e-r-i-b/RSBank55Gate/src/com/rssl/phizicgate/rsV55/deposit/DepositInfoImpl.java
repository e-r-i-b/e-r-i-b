package com.rssl.phizicgate.rsV55.deposit;

import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Danilov
 * @ created 28.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositInfoImpl implements DepositInfo
{
	private Account account;
	private Account percentAccount;
	private Map<Account, BigDecimal> finalAccounts;
	private Money   minBalance;
	private Money   minReplenishmentAmount;
	private boolean isRenewalAllowed;
	private String  agreementNumber;
	private boolean anticipatoryRemoval;
	private boolean additionalFee;

	public boolean isAnticipatoryRemoval()
	{
		return anticipatoryRemoval;
	}

	public void setAnticipatoryRemoval(boolean anticipatoryRemoval)
	{
		this.anticipatoryRemoval = anticipatoryRemoval;
	}

	public boolean isAdditionalFee()
	{
		return additionalFee;
	}

	public void setAdditionalFee(boolean additionalFee)
	{
		this.additionalFee = additionalFee;
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Account getPercentAccount()
	{
//		throw new UnsupportedOperationException();
		return percentAccount;
	}

	public void setPercentAccount(Account percentAccount)
	{
		this.percentAccount = percentAccount;
	}

	public Map<Account, BigDecimal> getFinalAccounts()
	{
		return finalAccounts;
	}

	public void setFinalAccounts(Map<Account, BigDecimal> finalAccounts)
	{
		this.finalAccounts = finalAccounts;
	}

	public Money getMinBalance()
	{
		return minBalance;
	}

	public void setMinBalance(Money minBalance)
	{
		this.minBalance = minBalance;
	}

	public Money getMinReplenishmentAmount()
	{
		return minReplenishmentAmount;
	}

	public void setMinReplenishmentAmount(Money minReplenishmentAmount)
	{
		this.minReplenishmentAmount = minReplenishmentAmount;
	}

	public boolean isRenewalAllowed()
	{
		return isRenewalAllowed;
	}

	public void setRenewalAllowed(boolean renewalAllowed)
	{
		isRenewalAllowed = renewalAllowed;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}
}
