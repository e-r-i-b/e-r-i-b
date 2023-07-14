package com.rssl.phizicgate.manager.services.objects;

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
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class LoanBase implements Loan
{
	Loan delegate;

	public LoanBase(Loan delegate)
	{
		this.delegate = delegate;
	}

	public String getId()
	{
		return delegate.getId();
	}

	public LoanState getState()
	{
		return delegate.getState();
	}

	public String getDescription()
	{
		return delegate.getDescription();
	}

	public String getAgreementNumber()
	{
		return delegate.getAgreementNumber();
	}

	public String getAccountNumber()
	{
		return delegate.getAccountNumber();
	}

	public Calendar getTermStart()
	{
		return delegate.getTermStart();
	}

	public Calendar getTermEnd()
	{
		return delegate.getTermEnd();
	}

	public DateSpan getTermDuration()
	{
		return delegate.getTermDuration();
	}

	public Money getLoanAmount()
	{
		return delegate.getLoanAmount();
	}

	public BigDecimal getRate()
	{
		return delegate.getRate();
	}

	public Money getBalanceAmount()
	{
		return delegate.getBalanceAmount();
	}

	public Calendar getLastPaymentDate()
	{
		return delegate.getLastPaymentDate();
	}

	public Money getLastPaymentAmount()
	{
		return delegate.getLastPaymentAmount();
	}

	public Calendar getNextPaymentDate()
	{
		return delegate.getNextPaymentDate();
	}

	public Money getNextPaymentAmount()
	{
		return delegate.getNextPaymentAmount();
	}

	public Money getPastDueAmount()
	{
		return delegate.getPastDueAmount();
	}

	public PersonLoanRole getPersonRole()
	{
		return delegate.getPersonRole();
	}

	public boolean getIsAnnuity()
	{
		return delegate.getIsAnnuity();
	}

	public Client getBorrower()
	{
		return delegate.getBorrower();
	}

	public Office getOffice()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Money getFirstDelayPenalty()
	{
		return delegate.getFirstDelayPenalty();
	}

	public Money getSecondDelayPenalty()
	{
		return delegate.getSecondDelayPenalty();
	}

	public Money getThirdDelayPenalty()
	{
		return delegate.getThirdDelayPenalty();
	}

	public BigDecimal getCommissionRate()
	{
		return delegate.getCommissionRate();
	}

	public CommissionBase getCommissionBase()
	{
		return delegate.getCommissionBase();
	}

	public Iterator<GuaranteeContract> getGuaranteeContractIterator()
	{
		return delegate.getGuaranteeContractIterator();
	}

	public Map<PersonLoanRole, Client> getLoanPersons()
	{
		return delegate.getLoanPersons();
	}

    public String getProdType()
    {
        return delegate.getProdType();
    }

    public BigDecimal getCreditingRate()
    {
        return delegate.getCreditingRate();
    }

    public String getLoanType()
    {
        return delegate.getLoanType();
    }

    public Money getFullRepaymentAmount()
    {
        return delegate.getFullRepaymentAmount();
    }

    public Calendar getClosestPaymentDate()
    {
        return delegate.getClosestPaymentDate();
    }

    public Money getRecPayment()
    {
        return delegate.getRecPayment();
    }

    public Money getMainDebt()
    {
        return delegate.getMainDebt();
    }

    public Money getInterestPayments()
    {
        return delegate.getInterestPayments();
    }

    public Money getOverdueMainDebts()
    {
        return delegate.getOverdueMainDebts();
    }

    public Money getOverdueInterestDebts()
    {
        return delegate.getOverdueInterestDebts();
    }

    public List<String> getAccount()
    {
        return delegate.getAccount();
    }

    public Money getMainDebtAmount()
    {
        return delegate.getMainDebtAmount();
    }

    public Money getInterestPaymentsAmount()
    {
        return delegate.getInterestPaymentsAmount();
    }

    public String getAgencyAddress()
    {
        return delegate.getAgencyAddress();
    }

    public List<String> getNewBorrower()
    {
        return delegate.getNewBorrower();
    }

    public List<String> getNewCoBorrower()
    {
        return delegate.getNewCoBorrower();
    }

    public List<String> getGuarantor()
    {
        return delegate.getGuarantor();
    }

    public Long getLoanStatus()
    {
        return delegate.getLoanStatus();
    }

    public BigDecimal getPrincipalBalance()
    {
        return delegate.getPrincipalBalance();
    }

    public boolean isOverdue()
    {
        return delegate.isOverdue();
	}

	public boolean isBAKS()
	{
		return delegate.isBAKS();
	}


	public List<EarlyRepayment> getEarlyRepayments()
	{
		return delegate.getEarlyRepayments();
	}

	public boolean isAutoGranted()
	{
		return delegate.isAutoGranted();
	}

	public String getApplicationNumber()
	{
		return delegate.getApplicationNumber();
	}
}
