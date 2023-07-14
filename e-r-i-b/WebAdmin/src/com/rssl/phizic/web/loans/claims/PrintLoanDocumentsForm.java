package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.documents.LoanClaim;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author gladishev
 * @ created 06.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanDocumentsForm extends ActionFormBase
{
	private Long id;
	private LoanClaim claim;
	private Loan loan;
	private List documents;
	private List<ScheduleItem> schedule;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public LoanClaim getClaim()
	{
		return claim;
	}

	public void setClaim(LoanClaim claim)
	{
		this.claim = claim;
	}

	public Loan getLoan()
	{
		return loan;
	}

	public void setLoan(Loan loan)
	{
		this.loan = loan;
	}

	public List getDocuments()
	{
		return documents;
	}

	public void setDocuments(List documents)
	{
		this.documents = documents;
	}

	public List<ScheduleItem> getSchedule()
	{
		return schedule;
	}

	public void setSchedule(List<ScheduleItem> schedule)
	{
		this.schedule = schedule;
	}
}
