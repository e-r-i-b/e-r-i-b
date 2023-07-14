package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 23.10.2012
 * Time: 11:07:28
 */
public class StoredAccount extends AbstractStoredResource<Account, Void> implements Account
{
	private Calendar        openDate;
	private Boolean         demand;
	private Boolean         passbook;
	private Boolean         creditAllowed;
	private Boolean         debitAllowed;
	private Boolean         prolongationAllowed;
	private Calendar        closeDate;
	private BigDecimal      interestRate;
	private Money           balance;
	private Money           maxSumWrite;
	private Money           minimumBalance;
	private AccountState    accountState;
	private Long            kind;
	private Long            subKind;
	private String          interestTransferAccount;
	private String          interestTransferCard;
	private String          stringPeriod;
	private Boolean         creditCrossAgencyAllowed;
	private Boolean         debitCrossAgencyAllowed;
	private BigDecimal      clearBalance;
	private BigDecimal      maxBalance;
	private Calendar        prolongationDate;
	private Long            clientKind;

	private static final ClosedAccountFilter closedAccountFilter = new ClosedAccountFilter();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setCreditAllowed(Boolean creditAllowed)
	{
		this.creditAllowed = creditAllowed;
	}

	public Boolean getCreditAllowed()
	{
		return creditAllowed;
	}

	public void setDebitAllowed(Boolean debitAllowed)
	{
		this.debitAllowed = debitAllowed;
	}

	public Boolean getDebitAllowed()
	{
		return debitAllowed;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setMaxSumWrite(Money maxSumWrite)
	{
		this.maxSumWrite = maxSumWrite;
	}

	public Money getMaxSumWrite()
	{
		return maxSumWrite;
	}

	public void setMinimumBalance(Money minimumBalance)
	{
		this.minimumBalance = minimumBalance;
	}

	public Money getMinimumBalance()
	{
		return minimumBalance;
	}

	public void setAccountState(AccountState accountState)
	{
		this.accountState = accountState;
	}

	public AccountState getAccountState()
	{
		return accountState;
	}

	public void setKind(Long kind)
	{
		this.kind = kind;
	}

	public Long getKind()
	{
		return kind;
	}

	public void setSubKind(Long subKind)
	{
		this.subKind = subKind;
	}

	public Long getSubKind()
	{
		return subKind;
	}

	public void setDemand(Boolean demand)
	{
		this.demand = demand;
	}

	public Boolean getDemand()
	{
		return demand;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public Calendar getCloseDate()
	{
		return closeDate;
	}

	public void setPassbook(Boolean passbook)
	{
		this.passbook = passbook;
	}

	public Boolean getPassbook()
	{
		return passbook;
	}

	public void setProlongationAllowed(Boolean prolongationAllowed)
	{
		this.prolongationAllowed = prolongationAllowed;
	}

	public Boolean getProlongationAllowed()
	{
		return prolongationAllowed;
	}

	public void setInterestTransferAccount(String interestTransferAccount)
	{
		this.interestTransferAccount = interestTransferAccount;
	}

	public String getInterestTransferAccount()
	{
		return interestTransferAccount;
	}

	public void setInterestTransferCard(String interestTransferCard)
	{
		this.interestTransferCard = interestTransferCard;
	}

	public String getInterestTransferCard()
	{
		return interestTransferCard;
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

	public BigDecimal getMaxBalance()
	{
		return maxBalance;
	}

	public void setMaxBalance(BigDecimal maxBalance)
	{
		this.maxBalance = maxBalance;
	}

	public BigDecimal getClearBalance()
	{
		return clearBalance;
	}

	public void setClearBalance(BigDecimal clearBalance)
	{
		this.clearBalance = clearBalance;
	}

	public DateSpan getPeriod()
	{
		if (StringHelper.isEmpty(stringPeriod))
		{
			return null;
		}

		String[] periodPart = stringPeriod.split("-");

		return new DateSpan(Integer.valueOf(periodPart[0]),
							Integer.valueOf(periodPart[1]),
							Integer.valueOf(periodPart[2]));
	}

	public String getStringPeriod()
	{
		return stringPeriod;
	}

	public void setStringPeriod(String stringPeriod)
	{
		this.stringPeriod = stringPeriod;
	}

	public String getId()
	{
		return getResourceLink().getExternalId();
	}

	public String getNumber()
	{
		return getResourceLink().getNumber();
	}

	public String getDescription()
	{
		return getResourceLink().getDescription();
	}

	public Currency getCurrency()
	{
		return getResourceLink().getCurrency();
	}

	public Client getAccountClient()
	{
		return MOCK_CLIENT;
	}

	public Calendar getProlongationDate()
	{
		return prolongationDate;
	}

	public void setProlongationDate(Calendar prolongationDate)
	{
		this.prolongationDate = prolongationDate;
	}

	public void update(Account account)
	{
		// Проверка на адекватость даты
		if (account.getOpenDate() != null && !account.getOpenDate().before(AccountsUtil.DEMAND_ACCOUNTS_END_DATE))
			this.openDate = account.getOpenDate();
		else
			this.openDate = null;
		this.debitAllowed   = account.getDebitAllowed();
		this.creditAllowed  = account.getCreditAllowed();
		this.interestRate   = account.getInterestRate();
		this.balance        = account.getBalance();
		this.maxSumWrite    = account.getMaxSumWrite();
		this.minimumBalance = account.getMinimumBalance();
		this.accountState   = account.getAccountState();
		this.kind           = account.getKind();
		this.subKind        = account.getSubKind();
		this.demand         = account.getDemand();
		this.passbook       = account.getPassbook();
		this.clientKind       = account.getClientKind();

		// Проверка на адекватность даты
		if (account.getCloseDate() != null && !account.getCloseDate().before(AccountsUtil.DEMAND_ACCOUNTS_END_DATE))
		{
			this.closeDate = account.getCloseDate();
		}
		else
		{
			this.closeDate = null;
		}

		this.prolongationAllowed     = account.getProlongationAllowed();
		this.interestTransferAccount = account.getInterestTransferAccount();
		this.interestTransferCard    = account.getInterestTransferCard();
		this.clearBalance            = account.getClearBalance();
		this.maxBalance              = account.getMaxBalance();

		DateSpan period = account.getPeriod();
		if (period != null)
		{
			this.stringPeriod = String.format("%1$02d-%2$02d-%3$d", period.getYears(), period.getMonths(), period.getDays());
		}

		this.creditCrossAgencyAllowed = account.getCreditCrossAgencyAllowed();
		this.debitCrossAgencyAllowed  = account.getDebitCrossAgencyAllowed();
		this.prolongationDate  = account.getProlongationDate();

		updateOffice(account.getOffice());
		setEntityUpdateTime(Calendar.getInstance());
	}

	public boolean needUpdate(Account account)
	{
		// Сравниваем не все свойства, т.к. на входе продукт обновляется безусловно
		if (!NumericUtil.equalsNullIgnore(interestRate, account.getInterestRate()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(balance, account.getBalance()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(maxSumWrite, account.getMaxSumWrite()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(minimumBalance, account.getMinimumBalance()))
			return true;

		if (accountState != account.getAccountState())
			return true;

		if (DateHelper.nullSafeCompare(closeDate, account.getCloseDate()) != 0)
			return true;

		if (prolongationAllowed != null && account.getProlongationAllowed() != null &&
				!prolongationAllowed.equals(account.getProlongationAllowed()))
			return true;

		if (creditCrossAgencyAllowed != null && account.getCreditCrossAgencyAllowed() != null &&
				!creditCrossAgencyAllowed.equals(account.getCreditCrossAgencyAllowed()))
			return true;

		if (debitCrossAgencyAllowed != null && account.getDebitCrossAgencyAllowed() != null &&
				!debitCrossAgencyAllowed.equals(account.getDebitCrossAgencyAllowed()))
			return true;

		if (account.getInterestTransferAccount() != null && !StringHelper.equalsNullIgnore(account.getInterestTransferAccount(), interestTransferAccount))
			return true;

		if (account.getInterestTransferCard() != null && !StringHelper.equalsNullIgnore(account.getInterestTransferAccount(), interestTransferCard))
			return true;

		if (account.getClearBalance() != null && !account.getClearBalance().equals(clearBalance))
			return true;

		if (account.getMaxBalance() != null && !account.getMaxBalance().equals(maxBalance))
			return true;

		return false;
	}

	/**
	 *
	 * Неиспользуемые свойства
	 *
	 **/
	public String getType()	                        {	return null;	}
	public Calendar getLastTransactionDate()    	{	return null;	}
	public String getAgreementNumber()          	{	return null;	}

    public Long getClientKind()
    {
        return clientKind;
    }

    public void setClientKind(Long clientKind)
    {
        this.clientKind = clientKind;
    }
}
