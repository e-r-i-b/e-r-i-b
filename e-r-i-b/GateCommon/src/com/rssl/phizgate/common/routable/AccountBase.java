package com.rssl.phizgate.common.routable;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class AccountBase implements Account, Routable
{
	protected String id;
	protected String description;
	protected String type;
	protected String number;
	protected Currency currency;
	protected Calendar openDate;
	protected Boolean creditAllowed;
	protected Boolean debitAllowed;
	protected BigDecimal interestRate;
	protected Money balance;
	protected Money maxSumWrite;
	protected AccountState accountState;
	protected Office office;
	protected Long kind;
	protected Long subKind;
	protected Money minimumBalance;
	protected Boolean demand;
	protected Boolean passbook;
	protected String     agreementNumber;
	protected String     interestTransferAccount;
	protected String     interestTransferCard;
	protected Boolean    creditCrossAgencyAllowed;
	protected Boolean    debitCrossAgencyAllowed;
	protected Boolean    prolongationAllowed;
	protected BigDecimal clearBalance;
	protected BigDecimal maxBalance;
	protected Calendar   closeDate;
	protected Calendar   lastTransactionDate;
	protected DateSpan   period;
	protected Client     accountClient;
	protected Calendar   prolongationDate;
	protected Long       clientKind;

	public void storeRouteInfo(String info)
	{
		setId(IDHelper.storeRouteInfo(getId(), info));
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo(getId());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo(id);
		setId(IDHelper.restoreOriginalId(id));

		return info;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Boolean getCreditAllowed()
	{
		return creditAllowed;
	}

	public void setCreditAllowed(Boolean creditAllowed)
	{
		this.creditAllowed = creditAllowed;
	}

	public Boolean getDebitAllowed()
	{
		return debitAllowed;
	}

	public void setDebitAllowed(Boolean debitAllowed)
	{
		this.debitAllowed = debitAllowed;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public AccountState getAccountState()
	{
		return accountState;
	}

	public void setAccountState(String accountState)
	{
		setAccountState(AccountState.valueOf(accountState));
	}

	public void setAccountState(AccountState accountState)
	{
		this.accountState = accountState;
	}

	public Money getMaxSumWrite()
	{
		return maxSumWrite;
	}

	public void setMaxSumWrite(Money maxSumWrite)
	{
		this.maxSumWrite = maxSumWrite;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public Long getKind()
	{
		return kind;
	}

	public void setKind(Long kind)
	{
		this.kind = kind;
	}

	public Long getSubKind()
	{
		return subKind;
	}

	public void setSubKind(Long subKind)
	{
		this.subKind = subKind;
	}

	public Money getMinimumBalance()
	{
		return minimumBalance;
	}

	public void setMinimumBalance(Money minimumBalance)
	{
		this.minimumBalance = minimumBalance;
	}

	public Boolean getDemand()
	{
		return demand;
	}

	public void setDemand(Boolean demand)
	{
		this.demand = demand;
	}

	public Boolean getPassbook()
	{
		return (passbook == null || passbook);
	}

	public void setPassbook(Boolean passbook)
	{
		this.passbook = passbook;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public Calendar getCloseDate()
	{
		return closeDate;
	}

	public DateSpan getPeriod()
	{
		return period;
	}

	public Boolean getCreditCrossAgencyAllowed()
	{
		return creditCrossAgencyAllowed;
	}

	public Boolean getDebitCrossAgencyAllowed()
	{
		return debitCrossAgencyAllowed;
	}

	public Boolean getProlongationAllowed()
	{
		return prolongationAllowed;
	}

	public String getInterestTransferAccount()
	{
		return interestTransferAccount;
	}

	public String getInterestTransferCard()
	{
		return interestTransferCard;
	}

	public BigDecimal getClearBalance()
	{
		return clearBalance;
	}

	public BigDecimal getMaxBalance()
	{
		return maxBalance;
	}

	public Calendar getLastTransactionDate()
	{
		return lastTransactionDate;
	}

	public Client getAccountClient()
	{
		return accountClient;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public void setPeriod(DateSpan period)
	{
		this.period = period;
	}

	public void setCreditCrossAgencyAllowed(Boolean creditCrossAgencyAllowed)
	{
		this.creditCrossAgencyAllowed = creditCrossAgencyAllowed;
	}

	public void setDebitCrossAgencyAllowed(Boolean debitCrossAgencyAllowed)
	{
		this.debitCrossAgencyAllowed = debitCrossAgencyAllowed;
	}

	public void setProlongationAllowed(Boolean prolongationAllowed)
	{
		this.prolongationAllowed = prolongationAllowed;
	}

	public void setInterestTransferAccount(String interestTransferAccount)
	{
		this.interestTransferAccount = interestTransferAccount;
	}

	public void setInterestTransferCard(String interestTransferCard)
	{
		this.interestTransferCard = interestTransferCard;
	}

	public void setClearBalance(BigDecimal clearBalance)
	{
		this.clearBalance = clearBalance;
	}

	public void setMaxBalance(BigDecimal maxBalance)
	{
		this.maxBalance = maxBalance;
	}

	public void setLastTransactionDate(Calendar lastTransactionDate)
	{
		this.lastTransactionDate = lastTransactionDate;
	}

	public void setAccountClient(Client accountClient)
	{
		this.accountClient = accountClient;
	}

	public Calendar getProlongationDate()
	{
		return prolongationDate;
	}

	public void setProlongationDate(Calendar prolongationDate)
	{
		this.prolongationDate = prolongationDate;
	}

    public Long getClientKind()
    {
        return clientKind;
    }

    public void setClientKind(Long clientKind)
    {
        this.clientKind = clientKind;
    }
}