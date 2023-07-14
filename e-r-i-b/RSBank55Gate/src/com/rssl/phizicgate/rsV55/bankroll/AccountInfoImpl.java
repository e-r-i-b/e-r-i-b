package com.rssl.phizicgate.rsV55.bankroll;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountInfo;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Kidyaev
 * @ created 13.10.2005
 * @ $Author: hudyakov $
 * @ $Revision$
 */
public class AccountInfoImpl implements AccountInfo
{
    private long     accountId;
    private Calendar lastTransactionDate;
	private Calendar closeDate;
	private String   agreementNumber;
	private Boolean passbook;
	private DateSpan period;
	private Boolean prolongationAllowed;
	private String interestTransferAccount;
	private String interestTransferCard;
	private Boolean creditCrossAgencyAllowed;
	private Boolean debitCrossAgencyAllowed;
	private BigDecimal clearBalance;
	private BigDecimal maxBalance;

	AccountInfoImpl()
    {
    }

    AccountInfoImpl(Account account)
    {
        AccountImpl accountImpl = (AccountImpl) account;
        accountId   = accountImpl.getLongId();
    }

	public Calendar getLastTransactionDate()
    {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(Calendar lastTransactionDate)
    {
        this.lastTransactionDate = lastTransactionDate;
    }

	public long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

	public Calendar getCloseDate()
	{
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber.trim();
	}

	public Boolean getPassbook()
	{
		return passbook;
	}

	public void setPassbook(Boolean passbook)
	{
		this.passbook = passbook;
	}

	public DateSpan getPeriod()
	{
		return period;
	}

	public void setPeriod(DateSpan period)
	{
		this.period = period;
	}

	public Boolean getProlongationAllowed()
	{
		return prolongationAllowed;
	}

	public void setProlongationAllowed(Boolean prolongationAllowed)
	{
		this.prolongationAllowed = prolongationAllowed;
	}

	public String getInterestTransferAccount()
	{
		return interestTransferAccount;
	}

	public void setInterestTransferAccount(String interestTransferAccount)
	{
		this.interestTransferAccount = interestTransferAccount;
	}

	public String getInterestTransferCard()
	{
		return interestTransferCard;
	}

	public void setInterestTransferCard(String interestTransferCard)
	{
		this.interestTransferCard = interestTransferCard;
	}

	public Boolean getCreditCrossAgencyAllowed()
	{
		return creditCrossAgencyAllowed;
	}

	public void setCreditCrossAgencyAllowed(Boolean creditCrossAgencyAllowed)
	{
		this.creditCrossAgencyAllowed = creditCrossAgencyAllowed;
	}

	public Boolean getDebitCrossAgencyAllowed()
	{
		return debitCrossAgencyAllowed;
	}

	public void setDebitCrossAgencyAllowed(Boolean debitCrossAgencyAllowed)
	{
		this.debitCrossAgencyAllowed = debitCrossAgencyAllowed;
	}

	public BigDecimal getClearBalance()
	{
		return clearBalance;
	}

	public void setClearBalance(BigDecimal clearBalance)
	{
		this.clearBalance = clearBalance;
	}

	public BigDecimal getMaxBalance()
	{
		return maxBalance;
	}

	public void setMaxBalance(BigDecimal maxBalance)
	{
		this.maxBalance = maxBalance;
	}
}
