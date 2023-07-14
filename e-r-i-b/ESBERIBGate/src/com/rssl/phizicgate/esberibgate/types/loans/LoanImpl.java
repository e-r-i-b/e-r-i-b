package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import java.util.Calendar;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 14.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanImpl implements Loan
{
	private String id;
	private LoanState state;
	private String description;
	private String agreementNumber;
	private Calendar termStart;
	private DateSpan termDuration;
	private Calendar termEnd;
	private Money loanAmount;
	private BigDecimal rate;
	private Money balanceAmount;
	private Calendar nextPaymentDate;
	private Money nextPaymentAmount;
	private Calendar lastPaymentDate;
	private Money lastPaymentAmount;
	private Money pastDueAmount;
	private boolean isAnnuity;
	private PersonLoanRole personRole;
	private String accountNumber;
	private Client borrower;
	private Office office;

	private Money firstDelayPenalty;
	private Money secondDelayPenalty;
	private Money thirdDelayPenalty;
	private BigDecimal commissionRate;
	private CommissionBase commissionBase;
	private Iterator<GuaranteeContract> guaranteeContractIterator;
	private Map<PersonLoanRole, Client> loanPersons;

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

	public LoanImpl() {}

   /**
    * Внешний ID кредита
    * Domain: ExternalID
    *
    * @return ID
    */
	public String getId()
	{
		return id;
	}

   /**
    * Текущее состояние
    *
    *
    * @return состояние
    */
	public LoanState getState()
	{
		return state;
	}

   /**
    * Описание (Автокредит, Потребительский etc)
    *
    * Domain: Text
    *
    * @return текст
    */
	public String getDescription()
	{
		return description;
	}

   /**
    * Номер договора
    *
    * Domain: Text
    *
    * @return текст
    */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

   /**
    * Дата открытия договора
    *
    * Domain: Date
    *
    * @return дата
    */
	public Calendar getTermStart()
	{
		return termStart;
	}

   /**
    * Срок кредита
    *
    *
    * @return срок
    */
	public DateSpan getTermDuration()
	{
		return termDuration;
	}

   /**
    * Дата окончания договора
    *
    * Domain: Date
    *
    * @return дата
    */
	public Calendar getTermEnd()
	{
		return termEnd;
	}

   /**
    * Сумма кредита
    *
    *
    * @return сумма
    */
	public Money getLoanAmount()
	{
		return loanAmount;
	}

   /**
    * % ставка (% годовых)
    *
    * Domain: Percent
    *
    * @return
    */
	public BigDecimal getRate()
	{
		return rate;
	}

   /**
    * Сумма остатка
    *
    *
    * @return сумма
    */
	public Money getBalanceAmount()
	{
		return balanceAmount;
	}

   /**
    * Дата следующего платежа
    *
    * Domain: DateTime
    *
    * @return дата
    */
	public Calendar getNextPaymentDate()
	{
		return nextPaymentDate;
	}

   /**
    * Сумма следующего платежа
    *
    *
    * @return сумма
    */
	public Money getNextPaymentAmount()
	{
		return nextPaymentAmount;
	}

   /**
    * Дата последнего платежа
    *
    * Domain: DateTime
    *
    * @return дата
    */
	public Calendar getLastPaymentDate()
	{
		return lastPaymentDate;
	}

   /**
    * Сумма последнего платежа
    *
    *
    * @return сумма
    */
	public Money getLastPaymentAmount()
	{
		return lastPaymentAmount;
	}

   /**
    * Сумма просроченной задолженности
    *
    *
    * @return сумма
    */
	public Money getPastDueAmount()
	{
		return pastDueAmount;
	}

	public boolean getIsAnnuity()
	{
		return isAnnuity;
	}

	public Client getBorrower()
	{
		return borrower;
	}

	public Office getOffice()
	{
		return office;
	}

	public PersonLoanRole getPersonRole()
	{
		return personRole;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public Money getFirstDelayPenalty()
	{
		return firstDelayPenalty;
	}

	public Money getSecondDelayPenalty()
	{
		return secondDelayPenalty;
	}

	public Money getThirdDelayPenalty()
	{
		return thirdDelayPenalty;
	}

	public BigDecimal getCommissionRate()
	{
		return commissionRate;
	}

	public CommissionBase getCommissionBase()
	{
		return commissionBase;
	}

	public Iterator<GuaranteeContract> getGuaranteeContractIterator()
	{
		return guaranteeContractIterator;
	}

	public Map<PersonLoanRole, Client> getLoanPersons()
	{
		return loanPersons;
	}

	public void setFirstDelayPenalty(Money firstDelayPenalty)
	{
		this.firstDelayPenalty = firstDelayPenalty;
	}

	public void setSecondDelayPenalty(Money secondDelayPenalty)
	{
		this.secondDelayPenalty = secondDelayPenalty;
	}

	public void setThirdDelayPenalty(Money thirdDelayPenalty)
	{
		this.thirdDelayPenalty = thirdDelayPenalty;
	}

	public void setCommissionRate(BigDecimal commissionRate)
	{
		this.commissionRate = commissionRate;
	}

	public void setCommissionBase(CommissionBase commissionBase)
	{
		this.commissionBase = commissionBase;
	}

	public void setGuaranteeContractIterator(Iterator<GuaranteeContract> guaranteeContractIterator)
	{
		this.guaranteeContractIterator = guaranteeContractIterator;
	}

	public void setLoanPersons(Map<PersonLoanRole, Client> loanPersons)
	{
		this.loanPersons = loanPersons;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setState(LoanState state)
	{
		this.state = state;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public void setTermStart(Calendar termStart)
	{
		this.termStart = termStart;
	}

	public void setTermDuration(DateSpan termDuration)
	{
		this.termDuration = termDuration;
	}

	public void setTermEnd(Calendar termEnd)
	{
		this.termEnd = termEnd;
	}

	public void setLoanAmount(Money loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public void setBalanceAmount(Money balanceAmount)
	{
		this.balanceAmount = balanceAmount;
	}

	public void setNextPaymentDate(Calendar nextPaymentDate)
	{
		this.nextPaymentDate = nextPaymentDate;
	}

	public void setNextPaymentAmount(Money nextPaymentAmount)
	{
		this.nextPaymentAmount = nextPaymentAmount;
	}

	public void setLastPaymentDate(Calendar lastPaymentDate)
	{
		this.lastPaymentDate = lastPaymentDate;
	}

	public void setLastPaymentAmount(Money lastPaymentAmount)
	{
		this.lastPaymentAmount = lastPaymentAmount;
	}

	public void setPastDueAmount(Money pastDueAmount)
	{
		this.pastDueAmount = pastDueAmount;
	}

	public void setAnnuity(boolean annuity)
	{
		isAnnuity = annuity;
	}

	public void setPersonRole(PersonLoanRole personRole)
	{
		this.personRole = personRole;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public void setBorrower(Client borrower)
	{
		this.borrower = borrower;
	}

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