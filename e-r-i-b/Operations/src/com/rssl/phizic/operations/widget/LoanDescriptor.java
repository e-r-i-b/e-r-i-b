package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.ScheduleAbstract;

/**
 * Используется в виджете "Ваши кредиты"
 * @ author Rtischeva
 * @ created 25.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoanDescriptor
{
	//кредит
	private LoanLink loanLink;
	//график платежей по кредиту
	private ScheduleAbstract scheduleAbstract;
	//просрочен ли кредит. если количество дней до оплаты кредита меньше количества дней, за которое нужно оповестить клиента об оплате кредита, то  overdue = true
	private boolean overdue;
	//осталось дней до оплаты
	private long daysLeft;

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

	public boolean isOverdue()
	{
		return overdue;
	}

	public void setOverdue(boolean overdue)
	{
		this.overdue = overdue;
	}

	public long getDaysLeft()
	{
		return daysLeft;
	}

	public void setDaysLeft(long daysLeft)
	{
		this.daysLeft = daysLeft;
	}
}
