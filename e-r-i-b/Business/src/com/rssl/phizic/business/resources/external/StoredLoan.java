package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Balovtsev
 * Date: 23.10.2012
 * Time: 11:38:17
 */
public class StoredLoan extends AbstractStoredResource<Loan, Void> implements Loan
{
	private Long        loanId;
	private Boolean     isAnnuity;
	private LoanState   state;
	private String      description;
	private String      agreementNumber;
	private Money       pastDueAmount;
	private Money       nextPaymentAmount;
	private Calendar    nextPaymentDate;
	private Money       lastPaymentAmount;
	private Calendar    termStart;
	private Calendar    termEnd;
	private DateSpan    termDuration;
	private Money       loanAmount;
	private BigDecimal  rate;
	private Money       balanceAmount;
	private Calendar    lastPaymentDate;

	private static final ActiveLoanFilter activeLoanFilter = new ActiveLoanFilter();

    private boolean overdue;
    private BigDecimal principalBalance;
    private Long loanStatus;
    private List<String> guarantor;
    private List<String> newCoBorrower;
    private List<String> newBorrower;
    private String agencyAddress;
    private Money interestPaymentsAmount;
    private Money mainDebtAmount;
    private List<String> account;
    private Money overdueInterestDebts;
    private Money overdueMainDebts;
    private Money interestPayments;
    private Money mainDebt;
    private Money recPayment;
    private Calendar closestPaymentDate;
    private Money fullRepaymentAmount;
    private String loanType;
    private BigDecimal creditingRate;
    private String prodType;
	private boolean BAKS;
	private List<EarlyRepayment> earlyRepayments;
	private boolean autoGranted;
	private String applicationNumber;

	public Long getLoanId()
	{
		return loanId;
	}

	public void setLoanId(Long loanId)
	{
		this.loanId = loanId;
	}

	public void setState(LoanState state)
	{
		this.state = state;
	}

	public LoanState getState()
	{
		return state;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setTermStart(Calendar termStart)
	{
		this.termStart = termStart;
	}

	public Calendar getTermStart()
	{
		return termStart;
	}

	public void setTermEnd(Calendar termEnd)
	{
		this.termEnd = termEnd;
	}

	public Calendar getTermEnd()
	{
		return termEnd;
	}

	public void setTermDuration(DateSpan termDuration)
	{
		this.termDuration = termDuration;
	}

	public DateSpan getTermDuration()
	{
		return termDuration;
	}

	public void setLoanAmount(Money loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public Money getLoanAmount()
	{
		return loanAmount;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setBalanceAmount(Money balanceAmount)
	{
		this.balanceAmount = balanceAmount;
	}

	public Money getBalanceAmount()
	{
		return balanceAmount;
	}

	public void setLastPaymentDate(Calendar lastPaymentDate)
	{
		this.lastPaymentDate = lastPaymentDate;
	}

	public Calendar getLastPaymentDate()
	{
		return lastPaymentDate;
	}

	public void setLastPaymentAmount(Money lastPaymentAmount)
	{
		this.lastPaymentAmount = lastPaymentAmount;
	}

	public Money getLastPaymentAmount()
	{
		return lastPaymentAmount;
	}

	public void setNextPaymentDate(Calendar nextPaymentDate)
	{
		this.nextPaymentDate = nextPaymentDate;
	}

	public Calendar getNextPaymentDate()
	{
		return nextPaymentDate;
	}

	public void setNextPaymentAmount(Money nextPaymentAmount)
	{
		this.nextPaymentAmount = nextPaymentAmount;
	}

	public Money getNextPaymentAmount()
	{
		return nextPaymentAmount;
	}

	public void setPastDueAmount(Money pastDueAmount)
	{
		this.pastDueAmount = pastDueAmount;
	}

	public Money getPastDueAmount()
	{
		return pastDueAmount;
	}

	public void setIsAnnuity(Boolean annuity)
	{
		isAnnuity = annuity;
	}

	public boolean getIsAnnuity()
	{
		return isAnnuity;
	}

	public String getId()
	{
		return getResourceLink().getExternalId();
	}

	public String getAccountNumber()
	{
		return getResourceLink().getNumber();
	}

	public void update(Loan loan)
	{
		this.isAnnuity          = loan.getIsAnnuity();
		this.state              = loan.getState();
		this.description        = loan.getDescription();
		this.agreementNumber    = loan.getAgreementNumber();
		this.pastDueAmount      = loan.getPastDueAmount();
		this.nextPaymentAmount  = loan.getNextPaymentAmount();
		this.nextPaymentDate    = loan.getNextPaymentDate();
		this.lastPaymentAmount  = loan.getLastPaymentAmount();
		this.termStart          = loan.getTermStart();
		this.termEnd            = loan.getTermEnd();
		this.termDuration       = loan.getTermDuration();
		this.loanAmount         = loan.getLoanAmount();
		this.rate               = loan.getRate();
		this.balanceAmount      = loan.getBalanceAmount();
		this.lastPaymentDate    = loan.getLastPaymentDate();

		this.overdue                = loan.isOverdue();
		this.principalBalance       = loan.getPrincipalBalance();
		this.loanStatus             = loan.getLoanStatus();
		this.guarantor              = loan.getGuarantor();
		this.newCoBorrower          = loan.getNewCoBorrower();
		this.newBorrower            = loan.getNewBorrower();
		this.agencyAddress          = loan.getAgencyAddress();
		this.interestPaymentsAmount = loan.getInterestPaymentsAmount();
		this.mainDebtAmount         = loan.getMainDebtAmount();
		this.account                = loan.getAccount();
		this.overdueInterestDebts   = loan.getOverdueInterestDebts();
		this.overdueMainDebts       = loan.getOverdueMainDebts();
		this.interestPayments       = loan.getInterestPayments();
		this.mainDebt               = loan.getMainDebt();
		this.recPayment             = loan.getRecPayment();
		this.closestPaymentDate     = loan.getClosestPaymentDate();
		this.fullRepaymentAmount    = loan.getFullRepaymentAmount();
		this.loanType               = loan.getLoanType();
		this.creditingRate          = loan.getCreditingRate();
		this.prodType               = loan.getProdType();

		updateOffice(loan.getOffice());
		setEntityUpdateTime( Calendar.getInstance() );
	}

	public boolean needUpdate(Loan loan)
	{
		// Сравниваем не все свойства, т.к. на входе продукт обновляется безусловно
		if (state != loan.getState())
			return true;

		if (!MoneyUtil.equalsNullIgnore(pastDueAmount, loan.getPastDueAmount()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(nextPaymentAmount, loan.getNextPaymentAmount()))
			return true;

		if (DateHelper.nullSafeCompare(nextPaymentDate, loan.getNextPaymentDate()) != 0)
			return true;

		if (!MoneyUtil.equalsNullIgnore(lastPaymentAmount, loan.getLastPaymentAmount()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(loanAmount, loan.getLoanAmount()))
			return true;

		if (!NumericUtil.equalsNullIgnore(rate, loan.getRate()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(balanceAmount, loan.getBalanceAmount()))
			return true;

		if (DateHelper.nullSafeCompare(lastPaymentDate, loan.getLastPaymentDate()) != 0)
			return true;

		return false;
	}

	/**
	 *
	 * Неиспользуемые свойства
	 *
	 **/
	public PersonLoanRole getPersonRole()                           	{	return null;	}
	public Client getBorrower()                                     	{	return null;	}
	public Money getFirstDelayPenalty()                             	{	return null;	}
	public Money getSecondDelayPenalty()                            	{	return null;	}
	public Money getThirdDelayPenalty()                             	{	return null;	}
	public BigDecimal getCommissionRate()                           	{	return null;	}
	public CommissionBase getCommissionBase()                       	{	return null;	}
	public Iterator<GuaranteeContract> getGuaranteeContractIterator()   {	return null;	}
	public Map<PersonLoanRole, Client> getLoanPersons()             	{   return null;	}

    public boolean isOverdue()
    {
        return overdue;
    }

	public boolean isBAKS()
	{
		return BAKS;
	}

	public List<EarlyRepayment> getEarlyRepayments()
	{
		return earlyRepayments;
	}

	public void setOverdue(boolean overdue)
    {
        this.overdue = overdue;
    }

    public BigDecimal getPrincipalBalance()
    {
        return principalBalance;
    }

    public void setPrincipalBalance(BigDecimal principalBalance)
    {
        this.principalBalance = principalBalance;
    }

    public Long getLoanStatus()
    {
        return loanStatus;
    }

    public void setLoanStatus(Long loanStatus)
    {
        this.loanStatus = loanStatus;
    }

    public List<String> getGuarantor()
    {
        return guarantor;
    }

    public void setGuarantor(List<String> guarantor)
    {
        this.guarantor = guarantor;
    }

    public List<String> getNewCoBorrower()
    {
        return newCoBorrower;
    }

    public void setNewCoBorrower(List<String> newCoBorrower)
    {
        this.newCoBorrower = newCoBorrower;
    }

    public List<String> getNewBorrower()
    {
        return newBorrower;
    }

    public void setNewBorrower(List<String> newBorrower)
    {
        this.newBorrower = newBorrower;
    }

    public String getAgencyAddress()
    {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress)
    {
        this.agencyAddress = agencyAddress;
    }

    public Money getInterestPaymentsAmount()
    {
        return interestPaymentsAmount;
    }

    public void setInterestPaymentsAmount(Money interestPaymentsAmount)
    {
        this.interestPaymentsAmount = interestPaymentsAmount;
    }

    public Money getMainDebtAmount()
    {
        return mainDebtAmount;
    }

    public void setMainDebtAmount(Money mainDebtAmount)
    {
        this.mainDebtAmount = mainDebtAmount;
    }

    public List<String> getAccount()
    {
        return account;
    }

    public void setAccount(List<String> account)
    {
        this.account = account;
    }

    public Money getOverdueInterestDebts()
    {
        return overdueInterestDebts;
    }

    public void setOverdueInterestDebts(Money overdueInterestDebts)
    {
        this.overdueInterestDebts = overdueInterestDebts;
    }

    public Money getOverdueMainDebts()
    {
        return overdueMainDebts;
    }

    public void setOverdueMainDebts(Money overdueMainDebts)
    {
        this.overdueMainDebts = overdueMainDebts;
    }

    public Money getInterestPayments()
    {
        return interestPayments;
    }

    public void setInterestPayments(Money interestPayments)
    {
        this.interestPayments = interestPayments;
    }

    public Money getMainDebt()
    {
        return mainDebt;
    }

    public void setMainDebt(Money mainDebt)
    {
        this.mainDebt = mainDebt;
    }

    public Money getRecPayment()
    {
        return recPayment;
    }

    public void setRecPayment(Money recPayment)
    {
        this.recPayment = recPayment;
    }

    public Calendar getClosestPaymentDate()
    {
        return closestPaymentDate;
    }

    public void setClosestPaymentDate(Calendar closestPaymentDate)
    {
        this.closestPaymentDate = closestPaymentDate;
    }

    public Money getFullRepaymentAmount()
    {
        return fullRepaymentAmount;
    }

    public void setFullRepaymentAmount(Money fullRepaymentAmount)
    {
        this.fullRepaymentAmount = fullRepaymentAmount;
    }

    public String getLoanType()
    {
        return loanType;
    }

    public void setLoanType(String loanType)
    {
        this.loanType = loanType;
    }

    public BigDecimal getCreditingRate()
    {
        return creditingRate;
    }

    public void setCreditingRate(BigDecimal creditingRate)
    {
        this.creditingRate = creditingRate;
    }

    public String getProdType()
    {
        return prodType;
    }

    public void setProdType(String prodType)
    {
        this.prodType = prodType;
    }

	public void setBAKS(boolean BAKS)
	{
		this.BAKS = BAKS;
	}

	public void setEarlyRepayments(List<EarlyRepayment> earlyRepayments)
	{
		this.earlyRepayments = earlyRepayments;
	}

	public boolean isAutoGranted()
	{
		return autoGranted;
	}

	public void setAutoGranted(boolean autoGranted)
	{
		this.autoGranted = autoGranted;
	}

	public String getApplicationNumber()
	{
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber)
	{
		this.applicationNumber = applicationNumber;
	}
}
