package com.rssl.phizic.web.atm.ext.sbrf.loans;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.web.atm.common.FilterFormBase;

/**
 * @author mihaylov
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanInfoForm extends FilterFormBase
{
	private LoanLink loanLink;
	private ScheduleAbstract scheduleAbstract;

	private boolean isError = false;

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}

	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	public void setLoanLink(LoanLink loanLink)
	{
		this.loanLink = loanLink;
	}

	public ScheduleAbstract getScheduleAbstract()
	{
		return scheduleAbstract;
	}

	public void setScheduleAbstract(ScheduleAbstract scheduleAbstract)
	{
		this.scheduleAbstract = scheduleAbstract;
	}

}