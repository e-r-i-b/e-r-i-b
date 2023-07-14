package com.rssl.phizic.web.client.loans.loan;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author gladishev
 * @ created 30.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanDetailsForm extends ActionFormBase
{
	private Loan loan;
	private ScheduleItem scheduleItem;
	private List<ScheduleItem> schedule; //график погашения
	private ActivePerson person;

	public void setLoan(Loan loan)
	{
		this.loan = loan;
	}

	public Loan getLoan()
	{
		return loan;
	}

	public ScheduleItem getScheduleItem()
	{
		return scheduleItem;
	}

	public void setScheduleItem(ScheduleItem scheduleItem)
	{
		this.scheduleItem = scheduleItem;
	}

	public List<ScheduleItem> getSchedule()
	{
		return schedule;
	}

	public void setSchedule(List<ScheduleItem> schedule)
	{
		this.schedule = schedule;
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public void setPerson(ActivePerson person)
	{
		this.person = person;
	}
}
