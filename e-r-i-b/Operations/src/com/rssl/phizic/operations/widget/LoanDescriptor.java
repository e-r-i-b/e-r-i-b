package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.ScheduleAbstract;

/**
 * ������������ � ������� "���� �������"
 * @ author Rtischeva
 * @ created 25.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoanDescriptor
{
	//������
	private LoanLink loanLink;
	//������ �������� �� �������
	private ScheduleAbstract scheduleAbstract;
	//��������� �� ������. ���� ���������� ���� �� ������ ������� ������ ���������� ����, �� ������� ����� ���������� ������� �� ������ �������, ��  overdue = true
	private boolean overdue;
	//�������� ���� �� ������
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
