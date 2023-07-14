package com.rssl.phizic.business.loans.mock;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.loans.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockLoan implements Loan, MockObject
{
	private static String EMPTY_STRING="";

	String id = EMPTY_STRING;
	String accountNumber = EMPTY_STRING;
	private boolean BAKS;
	private List<EarlyRepayment> earlyRepayments;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public LoanState getState()
	{
		return null;
	}

	public String getDescription()
	{
		return null;
	}

	public String getAgreementNumber()
	{
		return null;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public Calendar getTermStart()
	{
		return null;
	}

	public Calendar getTermEnd()
	{
		return null;
	}

	public DateSpan getTermDuration()
	{
		return null;
	}

	public Money getLoanAmount()
	{
		return null;
	}

	public BigDecimal getRate()
	{
		return null;
	}

	public Money getBalanceAmount()
	{
		return null;
	}

	public Calendar getLastPaymentDate()
	{
		return null;
	}

	public Money getLastPaymentAmount()
	{
		return null;
	}

	public Calendar getNextPaymentDate()
	{
		return null;
	}

	public Money getNextPaymentAmount()
	{
		return null;
	}

	public Money getPastDueAmount()
	{
		return null;
	}

	public PersonLoanRole getPersonRole()
	{
		return null;
	}

	public boolean getIsAnnuity()
	{
		return false;
	}

	public Client getBorrower()
	{
		return null;
	}

	public Office getOffice()
	{
		return null;
	}

	public Money getFirstDelayPenalty()
	{
		return null;
	}

	public Money getSecondDelayPenalty()
	{
		return null;
	}

	public Money getThirdDelayPenalty()
	{
		return null;
	}

	public BigDecimal getCommissionRate()
	{
		return null;
	}

	public CommissionBase getCommissionBase()
	{
		return null;
	}

	public Iterator<GuaranteeContract> getGuaranteeContractIterator()
	{
		return null;
	}

	public Map<PersonLoanRole, Client> getLoanPersons()
	{
		return null;
	}

    public String getProdType()
    {
        return null;
    }

    public BigDecimal getCreditingRate()
    {
        return null;
    }

    public String getLoanType()
    {
        return null;
    }

    public Money getFullRepaymentAmount()
    {
        return null;
    }

    public Calendar getClosestPaymentDate()
    {
        return null;
    }

    public Money getRecPayment()
    {
        return null;
    }

    public Money getMainDebt()
    {
        return null;
    }

    public Money getInterestPayments()
    {
        return null;
    }

    public Money getOverdueMainDebts()
    {
        return null;
    }

    public Money getOverdueInterestDebts()
    {
        return null;
    }

    public List<String> getAccount()
    {
        return null;
    }

    public Money getMainDebtAmount()
    {
        return null;
    }

    public Money getInterestPaymentsAmount()
    {
        return null;
    }

    public String getAgencyAddress()
    {
        return null;
    }

    public List<String> getNewBorrower()
    {
        return null;
    }

    public List<String> getNewCoBorrower()
    {
        return null;
    }

    public List<String> getGuarantor()
    {
        return null;
    }

    public Long getLoanStatus()
    {
        return null;
    }

    public BigDecimal getPrincipalBalance()
    {
        return null;
    }

    public boolean isOverdue()
    {
        return false;
	}

	public boolean isBAKS()
	{
		return BAKS;
	}

	public List<EarlyRepayment> getEarlyRepayments()
	{
		return earlyRepayments;
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
		return false;
	}

	public String getApplicationNumber()
	{
		return null;
	}
}
