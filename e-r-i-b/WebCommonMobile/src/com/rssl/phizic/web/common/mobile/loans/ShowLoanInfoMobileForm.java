package com.rssl.phizic.web.common.mobile.loans;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.web.common.client.loans.ShowLoanInfoForm;

/**
 * Детальная информация по кредиту
 * @author mihaylov
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanInfoMobileForm extends ShowLoanInfoForm
{
    //in
    private String loanName;
    //out
	private LoanLink loanLink;
	private ScheduleAbstract scheduleAbstract;

    public String getLoanName()
    {
        return loanName;
    }

    public void setLoanName(String loanName)
    {
        this.loanName = loanName;
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