package com.rssl.phizicgate.mock.loans;

import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;

import java.util.*;
import java.math.BigDecimal;

/**
 * @author hudyakov
 * @ created 26.08.2008
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
	private int termDurationMonths;
	private boolean isAnnuity;
	private PersonLoanRole personRole;
	private String accountNumber;
	private Client borrower;

	private Money firstDelayPenalty;
	private Money secondDelayPenalty;
	private Money thirdDelayPenalty;
	private BigDecimal commissionRate;
	private CommissionBase commissionBase;
	private Iterator<GuaranteeContract> guaranteeContractIterator;
	private Map<PersonLoanRole, Client> loanPersons = new HashMap<PersonLoanRole, Client>();

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

	public LoanImpl(String id,
	                LoanState      state,
	                String         description,
	                String         agreementNumber,
	                Calendar       termStart,
	                DateSpan       termDuration,
	                Calendar       termEnd,
	                Money          loanAmount,
	                BigDecimal     rate,
	                Money          balanceAmount,
	                Calendar       nextPaymentDate,
	                Money          nextPaymentAmount,
	                Calendar       lastPaymentDate,
	                Money          lastPaymentAmount,
	                Money          pastDueAmount,
	                int            termDurationMonths,
	                PersonLoanRole personRole,
	                boolean        isAnnuity,
	                String         accountNumber,
	                Client         borrower,
	                Money          firstDelayPenalty,
	                Money          secondDelayPenalty,
			        Money          thirdDelayPenalty,
			        BigDecimal     commissionRate,
			        CommissionBase commissionBase,
			        Iterator<GuaranteeContract> guaranteeContractIterator,
			        Map<PersonLoanRole, Client> loanPersons
	)
	{
		this.termDurationMonths = termDurationMonths;
		this.pastDueAmount = pastDueAmount;
		this.lastPaymentAmount = lastPaymentAmount;
		this.lastPaymentDate = lastPaymentDate;
		this.nextPaymentAmount = nextPaymentAmount;
		this.nextPaymentDate = nextPaymentDate;
		this.balanceAmount = balanceAmount;
		this.rate = rate;
		this.loanAmount = loanAmount;
		this.termEnd = termEnd;
		this.termDuration = termDuration;
		this.termStart = termStart;
		this.agreementNumber = agreementNumber;
		this.description = description;
		this.state = state;
		this.id = id;
		this.isAnnuity = isAnnuity;
		this.personRole = personRole;
		this.accountNumber = accountNumber;
		this.borrower = borrower;
		this.firstDelayPenalty = firstDelayPenalty;
		this.secondDelayPenalty = secondDelayPenalty;
		this.thirdDelayPenalty = thirdDelayPenalty;
		this.commissionRate = commissionRate;
		this.commissionBase = commissionBase;
		this.guaranteeContractIterator = guaranteeContractIterator;
		this.loanPersons.putAll(loanPersons);
	}

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
    * Сумма следующего платежа
    *
    *
    * @return сумма
    */
	public Money getPastDueAmount()
	{
		return pastDueAmount;
	}

	public int getTermDurationMonths()
	{
		return termDurationMonths;
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
		return null;  //To change body of implemented methods use File | Settings | File Templates.
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

	public PersonLoanRole getPersonRole()
	{
		return personRole;
	}

	public String getAccountNumber()
	{
		return accountNumber;
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
